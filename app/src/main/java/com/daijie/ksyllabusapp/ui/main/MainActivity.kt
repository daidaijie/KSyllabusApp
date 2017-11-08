package com.daijie.ksyllabusapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import com.bigkoo.convenientbanner.ConvenientBanner
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.BannerImageHolderView
import com.daijie.ksyllabusapp.adapter.FunctionAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.data.Banner
import com.daijie.ksyllabusapp.data.Direction
import com.daijie.ksyllabusapp.data.FunctionItem
import com.daijie.ksyllabusapp.ui.dymatic.SchoolDymaticActivity
import com.daijie.ksyllabusapp.ext.deviceWidth
import com.daijie.ksyllabusapp.ext.dp2px
import com.daijie.ksyllabusapp.ext.getStringById
import com.daijie.ksyllabusapp.ext.toastW
import com.daijie.ksyllabusapp.ui.oa.list.OAListActivity
import com.daijie.ksyllabusapp.ui.syllabus.main.SyllabusActivity
import kotlinx.android.synthetic.main.activity_syllabus.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item_function_syllabus.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/18.
 */
class MainActivity : BaseActivity(), MainContract.View,
        NavigationView.OnNavigationItemSelectedListener {

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override var titleToolbar: Toolbar? = null
        get() = appToolbar
    override var titleText: TextView? = null
        get() = titleTextView

    @Inject
    lateinit var mainPresenter: MainPresenter

    private val bannerViewHolderCreator: BannerImageHolderView.BannerViewHolderCreator by lazy {
        BannerImageHolderView.BannerViewHolderCreator(this@MainActivity)
    }

    override val contentViewId = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getStringById(R.string.app_name))
        initDrawer()
        initBanner()
        initFunction()
        initInject()
        mainPresenter.subscribe()
    }

    override fun onResume() {
        super.onResume()
        convenientBanner.startTurning(3000)
    }

    override fun onStop() {
        convenientBanner.stopTurning()
        super.onStop()
    }

    override fun onDestroy() {
        mainPresenter.unsubscribe()
        super.onDestroy()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun showBanner(banners: List<Banner>) {
        if (banners.isNotEmpty()) {
            (convenientBanner as ConvenientBanner<Banner>).setPages(bannerViewHolderCreator, banners)
                    .setPageIndicator(BannerImageHolderView.sIndicator)
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
            convenientBanner.setcurrentitem((Math.random() * banners.size).toInt())
        }
    }

    private fun initDrawer() {
        val toggle = ActionBarDrawerToggle(this, drawer_layout, appToolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun initBanner() {
        convenientBanner.layoutParams.height = (deviceWidth - dp2px(8.0f)) * 9 / 16

    }


    private fun initFunction() {
        FunctionAdapter.setFunctionToView(
                FunctionItem.syllabusFunctionItem, functionCardView, functionTextView, Direction.LEFT
        )
        functionCardView.setOnClickListener {
            startActivity(SyllabusActivity.newIntent(this@MainActivity))
        }

        functionRecyclerView.isNestedScrollingEnabled = false
        functionRecyclerView.layoutManager = GridLayoutManager(this, 3)
        functionRecyclerView.adapter = FunctionAdapter().apply {
            setOnItemClickListener { adapter, view, position ->
                when (position) {
                    1 -> startActivity(OAListActivity.newIntent(this@MainActivity))
                    2 -> startActivity(SchoolDymaticActivity.newIntent(this@MainActivity))
                    else -> toastW("还没做")
                }
            }
        }
    }

    private fun initInject() {
        DaggerMainComponent.builder()
                .userComponent(App.userComponent)
                .mainModule(MainModule(this))
                .build().inject(this)
    }
}