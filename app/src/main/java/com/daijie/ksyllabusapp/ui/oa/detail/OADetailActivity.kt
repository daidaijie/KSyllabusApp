package com.daijie.ksyllabusapp.ui.oa.detail

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.OaFileAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.base.StoreProxy
import com.daijie.ksyllabusapp.data.OaFile
import com.daijie.ksyllabusapp.data.OaInfo
import com.daijie.ksyllabusapp.data.OnePageLoadDataStatus
import com.daijie.ksyllabusapp.ext.newDivLineItemDecoration
import kotlinx.android.synthetic.main.activity_oa_detail.*
import kotlinx.android.synthetic.main.header_oa_file.view.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-21.
 */
class OADetailActivity : BaseActivity(), OADetailContract.View {

    companion object {
        const val EXTRA_OA_INFO = "oaInfo"
        @JvmStatic
        fun newIntent(context: Context, oaInfo: OaInfo) = Intent(context, OADetailActivity::class.java).apply {
            putExtra(EXTRA_OA_INFO, oaInfo)
        }
    }

    override val contentViewId = R.layout.activity_oa_detail

    override var titleToolbar: Toolbar? = null
        get() = appToolbar
    override var titleText: TextView? = null
        get() = titleTextView

    private val oaFileAdapter: OaFileAdapter by lazy {
        OaFileAdapter(this@OADetailActivity, oaDetailRecyclerView)
    }

    private val headerView: View by lazy {
        layoutInflater.inflate(
                R.layout.header_oa_file, oaDetailRecyclerView.parent as ViewGroup, false
        )
    }

    private val oaInfo: OaInfo by lazy {
        intent.getParcelableExtra<OaInfo>(EXTRA_OA_INFO)
    }

    @Inject
    lateinit var oaDetailPresenter: OADetailPresenter

    private var loadWebViewEnd = false

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("详情")
        initLikeButton()
        StoreProxy.onRestoreInstanceState(savedInstanceState, oaFileAdapter)
        initWebView()
        initRecyclerView()
        initInject()
        oaDetailPresenter.subscribe()
    }

    override fun onDestroy() {
        oaDetailPresenter.unsubscribe()
        super.onDestroy()
    }

    override fun startLoading() {
        oaFileAdapter.showPre()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        StoreProxy.onSaveInstanceState(outState, oaFileAdapter)
    }


    override fun showSuccess(msg: String) {
        when (msg) {
            OnePageLoadDataStatus.MESSAGE_EMPTY_LOAD -> oaFileAdapter.showEmpty()
        }
    }

    override fun showError(msg: String) {
        when (msg) {
            OnePageLoadDataStatus.MESSAGE_ERROR_LOAD -> oaFileAdapter.showError()
        }
    }

    override fun showFiles(oaFiles: List<OaFile>) {
        oaFileAdapter.setNewData(oaFiles)
    }

    override fun showContent(context: String) {
        headerView.oADetailWebView.loadData(context, "text/html; charset=UTF-8", null)

    }

    private fun initInject() {
        DaggerOADetailComponent.builder()
                .appComponent(App.appComponent)
                .oADetailModule(OADetailModule(this, oaInfo))
                .build().inject(this)
    }

    private fun initRecyclerView() {
        oaFileAdapter.setHeaderAndEmpty(true)
        oaFileAdapter.addHeaderView(headerView)
        oaDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        oaDetailRecyclerView.addItemDecoration(newDivLineItemDecoration(R.drawable.dev_line))
        oaFileAdapter.bindToRecyclerView(oaDetailRecyclerView)
        oaFileAdapter.setOnErrorViewClickListener {
            oaDetailPresenter.loadOaFiles()
        }
        oaFileAdapter.restoreState()
    }

    private fun initWebView() {
        with(headerView) {
            with(oADetailWebView.settings) {
                loadWithOverviewMode = true
                defaultFontSize = 16
            }
            oADetailWebView.setBackgroundColor(0x01000000)
            oADetailWebView.isDrawingCacheEnabled = true
            oADetailWebView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    val objectAnimator = ObjectAnimator.ofFloat(oaDetailRecyclerView, "alpha", 0.0f, 1.0f)
                    objectAnimator.duration = 360
                    objectAnimator.interpolator = AccelerateInterpolator()
                    objectAnimator.start()
                    objectAnimator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            loadWebViewEnd = true
                            if (oaFileAdapter.isPre()) {
                                oaDetailPresenter.loadOaFiles()
                            }
                        }
                    })
                }
            }
        }
    }

    private fun initLikeButton() {
        val likeButton = layoutInflater.inflate(R.layout.view_white_star_button, appToolbar, true)
    }
}