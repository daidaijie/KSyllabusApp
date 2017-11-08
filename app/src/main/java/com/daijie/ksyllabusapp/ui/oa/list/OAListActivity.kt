package com.daijie.ksyllabusapp.ui.oa.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.OaListAdapter
import com.daijie.ksyllabusapp.adapter.OaListAdapter.Companion.PAGE_LIMIT
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.base.LoadDataViewImpl
import com.daijie.ksyllabusapp.base.LoadDataViewProxy
import com.daijie.ksyllabusapp.base.StoreProxy
import com.daijie.ksyllabusapp.data.OACompany
import com.daijie.ksyllabusapp.data.OaInfo
import com.daijie.ksyllabusapp.data.OaSearchInfo
import com.daijie.ksyllabusapp.ext.initColor
import com.daijie.ksyllabusapp.ext.newDivLineItemDecoration
import com.daijie.ksyllabusapp.ui.oa.detail.OADetailActivity
import com.daijie.ksyllabusapp.widgets.CustomLayoutManager
import kotlinx.android.synthetic.main.activity_oa_list.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/18.
 */
class OAListActivity : BaseActivity(), OAListContract.View, LoadDataViewProxy<OaInfo>,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    companion object {
        private const val EXTRA_OA_SEARCH_INFO = "oaSearchInfo"
        private const val EXTRA_IS_Main = "isMain"

        @JvmStatic
        fun newIntent(context: Context, oaSearchInfo: OaSearchInfo = OaSearchInfo(), isMain: Boolean = true)
                = Intent(context, OAListActivity::class.java).apply {
            putExtra(EXTRA_IS_Main, isMain)
            putExtra(EXTRA_OA_SEARCH_INFO, oaSearchInfo)
        }
    }

    override val contentViewId = R.layout.activity_oa_list

    override var titleToolbar: Toolbar? = null
        get() = appToolbar
    override var titleText: TextView? = null
        get() = titleTextView

    @Inject
    lateinit var oAListPresenter: OAListPresenter

    private var lastKeyword = ""
    private var lastSearchPosition = 0

    private val oaSearchInfo: OaSearchInfo by lazy {
        intent.getParcelableExtra<OaSearchInfo>(EXTRA_OA_SEARCH_INFO)
    }

    private val isMain: Boolean by lazy {
        intent.getBooleanExtra(EXTRA_IS_Main, true)
    }

    private val oaListAdapter: OaListAdapter by lazy {
        OaListAdapter(this@OAListActivity, true)
    }

    private val oaLayoutManager: CustomLayoutManager by lazy {
        CustomLayoutManager(this@OAListActivity).apply {
            setSpeedFast()
        }
    }

    override val loadDataViewImpl: LoadDataViewImpl<OaInfo>
        get() = LoadDataViewImpl(this@OAListActivity, oaListAdapter, oaLayoutManager, swipeRefreshLayout)

    override fun initView(savedInstanceState: Bundle?) {
        if (isMain) {
            initMainPage()
        } else {
            initSearchPage()
        }
        initRefreshLayout()
        StoreProxy.onRestoreInstanceState(savedInstanceState, oaListAdapter)
        initRecyclerView()
        initInject()

        if (oaListAdapter.isPre()) {
            swipeRefreshLayout.post {
                letPresenterLoadData()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        StoreProxy.onSaveInstanceState(outState, oaListAdapter)
    }

    private fun initSearchFab() {
        fab.setOnClickListener {
            val dialog = SearchOaDialog.newInstance(lastKeyword, lastSearchPosition)
            dialog.setonSearchButtonClickListener { position, keyword ->
                lastSearchPosition = position
                lastKeyword = keyword
                startActivity(OAListActivity.newIntent(this@OAListActivity, OaSearchInfo(
                        OACompany.oaCompanies[position].id,
                        lastKeyword
                ), isMain = false))
            }
            dialog.show(supportFragmentManager)
        }
    }

    override fun onDestroy() {
        oAListPresenter.dispose()
        super.onDestroy()
    }

    override fun onRefresh() {
        with(oaListAdapter) {
            currentPage = 0
            letPresenterLoadData()
        }
    }

    private fun initMainPage() {
        setTitle("办公自动化")
        initSearchFab()
    }

    private fun initSearchPage() {
        setTitle("搜索结果")
        fab.visibility = View.GONE
    }

    private fun initRefreshLayout() {
        swipeRefreshLayout.initColor()
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun initInject() {
        DaggerOAListComponent.builder()
                .appComponent(App.appComponent)
                .oAListModule(OAListModule(this, oaSearchInfo))
                .build().inject(this)
    }

    private fun initRecyclerView() {
        oaRecyclerView.layoutManager = oaLayoutManager
        oaRecyclerView.addItemDecoration(newDivLineItemDecoration(R.drawable.dev_line))
        oaRecyclerView.adapter = oaListAdapter
        oaListAdapter.setOnLoadMoreListener(this, oaRecyclerView)
        oaListAdapter.setOnItemClickListener { _, _, position ->
            startActivity(OADetailActivity.newIntent(
                    this@OAListActivity, oaListAdapter.data[position]
            ))
        }
        oaListAdapter.setOnErrorViewClickListener {
            letPresenterLoadData()
        }
        oaListAdapter.restoreState()
    }

    override fun onLoadMoreRequested() {
        letPresenterLoadData()
    }

    private fun letPresenterLoadData() {
        oAListPresenter.loadOaInfos(oaListAdapter.currentPage * PAGE_LIMIT, PAGE_LIMIT)
    }
}