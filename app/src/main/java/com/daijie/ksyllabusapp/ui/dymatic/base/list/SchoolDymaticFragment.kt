package com.daijie.ksyllabusapp.ui.dymatic.base.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.SchoolDymaticAdapter
import com.daijie.ksyllabusapp.base.BaseFragment
import com.daijie.ksyllabusapp.base.LoadDataViewImpl
import com.daijie.ksyllabusapp.base.LoadDataViewProxy
import com.daijie.ksyllabusapp.base.StoreProxy
import com.daijie.ksyllabusapp.data.SchoolDymaticData
import com.daijie.ksyllabusapp.ui.dymatic.base.delete.SchoolDymaticDeleteContract
import com.daijie.ksyllabusapp.ui.dymatic.base.delete.SchoolDymaticDeleteViewProxy
import com.daijie.ksyllabusapp.ui.dymatic.base.delete.SchoolDymaticiDeletePresenter
import com.daijie.ksyllabusapp.ui.dymatic.base.detail.SchoolDymaticDetailActivity
import com.daijie.ksyllabusapp.ui.dymatic.base.like.SchoolDymaticLikeContract
import com.daijie.ksyllabusapp.ui.dymatic.base.like.SchoolDymaticLikePresenter
import com.daijie.ksyllabusapp.ui.dymatic.base.like.SchoolDymaticLikeViewImpl
import com.daijie.ksyllabusapp.ext.clipboard
import com.daijie.ksyllabusapp.ext.initColor
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.ext.toastW
import com.daijie.ksyllabusapp.widgets.CustomLayoutManager
import kotlinx.android.synthetic.main.activity_oa_list.*
import kotlinx.android.synthetic.main.fragment_dymatic.*
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/24.
 */
abstract class SchoolDymaticFragment : BaseFragment(), LoadDataViewProxy<SchoolDymaticData>,
        SchoolDymaticContract.View, SchoolDymaticDeleteContract.IView,
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        DymaticDialogHandler.OnDymaticDialogSelectedListener {

    override val contentViewId = R.layout.fragment_dymatic

    abstract val schoolDymaticPresenter: SchoolDymaticContract.Presenter

    @Inject
    lateinit var schoolDymaticLikePresenter: SchoolDymaticLikePresenter

    @Inject
    lateinit var schoolDymaticDeletePresenter: SchoolDymaticiDeletePresenter

    protected val schoolDymaticLikeView: SchoolDymaticLikeViewImpl by lazy {
        SchoolDymaticLikeViewImpl(this)
    }

    protected val schoolDymaticDeleteView: SchoolDymaticDeleteViewProxy by lazy {
        SchoolDymaticDeleteViewProxy(this)
    }

    abstract val schoolDymaticAdapter: SchoolDymaticAdapter<*>

    private val schoolCircleLayoutManager: CustomLayoutManager by lazy {
        CustomLayoutManager(activity).apply {
            setSpeedFast()
        }
    }

    private val dymaticDialogHandler: DymaticDialogHandler by lazy {
        DymaticDialogHandler(act, this)
    }

    override val loadDataViewImpl: LoadDataViewImpl<SchoolDymaticData>by lazy {
        LoadDataViewImpl(activity, schoolDymaticAdapter, schoolCircleLayoutManager, dymaticSwipeRefreshLayout)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initSwipeRefreshLayout()
        StoreProxy.onRestoreInstanceState(savedInstanceState, schoolDymaticAdapter)
        initRecyclerView()
        initInject()
    }

    override fun onDestroyView() {
        schoolDymaticDeletePresenter.unsubscribe()
        schoolDymaticLikePresenter.unsubscribe()
        schoolDymaticPresenter.unsubscribe()
        super.onDestroyView()
    }

    override fun lazyInit(savedInstanceState: Bundle?) {
        super.lazyInit(savedInstanceState)
        if (schoolDymaticAdapter.isPre()) {
            letPresenterLoadData()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        StoreProxy.onSaveInstanceState(outState, schoolDymaticAdapter)
    }

    override fun onRefresh() {
        schoolDymaticAdapter.currentPage = 0
        letPresenterLoadData()
    }

    override fun onLoadMoreRequested() {
        letPresenterLoadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SchoolDymaticDetailActivity.REQUEST_DETAIL && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val position = data.getIntExtra(SchoolDymaticDetailActivity.EXTRA_DYMATIC_DATA_POSITION, -1)
                val newDatas = data.getParcelableExtra<SchoolDymaticData?>(SchoolDymaticDetailActivity.EXTRA_UPDATED_DYMATIC_DATA)
                if (position != -1 && newDatas != null) {
                    schoolDymaticAdapter.setData(position, newDatas)
                }
            }
        } else if (requestCode == SchoolDymaticDetailActivity.REQUEST_DETAIL && resultCode == SchoolDymaticDetailActivity.RESULT_DELETE_DYMATIC) {
            if (data != null) {
                val position = data.getIntExtra(SchoolDymaticDetailActivity.EXTRA_DYMATIC_DATA_POSITION, -1)
                if (position != -1) {
                    removeDymaticItem(position)
                }
            }
        }
    }

    private fun initRecyclerView() {
        dymaticRecyclerView.layoutManager = schoolCircleLayoutManager
        schoolDymaticAdapter.bindToRecyclerView(dymaticRecyclerView)
        schoolDymaticAdapter.setOnLoadMoreListener(this, oaRecyclerView)
        schoolDymaticAdapter.setOnItemClickListener { _, _, position ->

        }
        schoolDymaticAdapter.setOnErrorViewClickListener {
            letPresenterLoadData()
        }
        schoolDymaticAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.commentLayout -> checkRefresh({
                    toDetailActivity(schoolDymaticAdapter.data[position], position, true)
                })
            }
        }
        schoolDymaticAdapter.setOnItemClickListener { adapter, view, position ->
            checkRefresh({
                toDetailActivity(schoolDymaticAdapter.data[position], position, false)
            })
        }
        schoolDymaticAdapter.setOnItemLongClickListener { adapter, view, position ->
            checkRefresh({
                dymaticDialogHandler.handleDymaticDialog(schoolDymaticAdapter.data[position], position)
            })
            true
        }
        schoolDymaticAdapter.setLikeStatusChangeListener { schoolDymaticData: SchoolDymaticData, isLike, likeButton, likeTextView ->
            checkRefresh({
                schoolDymaticLikeView.handle(schoolDymaticData, likeButton, likeTextView, isLike)
            }, {
                likeButton.isLiked = !isLike
            })
        }
        schoolDymaticAdapter.restoreState()
    }


    override fun showSuccess(msg: String) {
        when (msg) {
            SchoolDymaticLikeContract.MESSAGE_LIKE_SUCCESS, SchoolDymaticLikeContract.MESSAGE_UNLIKE_SUCCESS -> {
            }
            SchoolDymaticDeleteContract.MESSAGE_DELETE_SCHOOL_DYMATIC_SUCCESS -> {
                activity.toastS(SchoolDymaticDeleteContract.MESSAGE_DELETE_SCHOOL_DYMATIC_SUCCESS)
            }
            else -> super.showSuccess(msg)
        }
    }

    override fun replyDymatic(schoolDymaticData: SchoolDymaticData, adapterPosition: Int) {
        toDetailActivity(schoolDymaticData, adapterPosition, true)
    }

    override fun deleteDymatic(schoolDymaticData: SchoolDymaticData, adapterPosition: Int) {
        schoolDymaticDeleteView.handleDeleteDymatic(schoolDymaticData, adapterPosition)
    }

    override fun removeDymaticItem(position: Int) {
        schoolDymaticAdapter.remove(position)
    }

    @CallSuper
    override fun copyDymaticText(schoolDymaticData: SchoolDymaticData) {
        clipboard = schoolDymaticAdapter.getDymaticContent(schoolDymaticData)
        act.toastS("复制成功")
    }

    private fun toDetailActivity(schoolDymaticData: SchoolDymaticData, adapterPosition: Int, showCommentDialog: Boolean) {
        startActivityForResult(SchoolDymaticDetailActivity.newIntent(
                activity, detailActivityClass, schoolDymaticData, adapterPosition, showCommentDialog
        ), SchoolDymaticDetailActivity.REQUEST_DETAIL)
    }

    private fun initSwipeRefreshLayout() {
        dymaticSwipeRefreshLayout.initColor()
        dymaticSwipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun checkRefresh(callback: () -> Unit, other: (() -> Unit)? = null) {
        if (schoolDymaticAdapter.isPre()) {
            activity.toastW("请等待刷新完成")
            other?.invoke()
        } else {
            callback.invoke()
        }
    }

    abstract fun initInject()
    abstract fun letPresenterLoadData()
    abstract val detailActivityClass: Class<out SchoolDymaticDetailActivity>
}