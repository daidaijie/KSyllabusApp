package com.daijie.ksyllabusapp.ui.dymatic.base.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.SchoolDymaticAdapter
import com.daijie.ksyllabusapp.adapter.SchoolDymaticCommentAdapter
import com.daijie.ksyllabusapp.base.*
import com.daijie.ksyllabusapp.data.OnePageLoadDataStatus.MESSAGE_SUCCESS_LOAD
import com.daijie.ksyllabusapp.data.SchoolDymaticCommentData
import com.daijie.ksyllabusapp.data.SchoolDymaticComments
import com.daijie.ksyllabusapp.data.SchoolDymaticData
import com.daijie.ksyllabusapp.ui.dymatic.base.delete.SchoolDymaticDeleteContract
import com.daijie.ksyllabusapp.ui.dymatic.base.delete.SchoolDymaticDeleteViewProxy
import com.daijie.ksyllabusapp.ui.dymatic.base.delete.SchoolDymaticiDeletePresenter
import com.daijie.ksyllabusapp.ui.dymatic.base.detail.SchoolDymaticCommentContract.Companion.MESSAGE_DELETE_COMMIT_SUCCESS
import com.daijie.ksyllabusapp.ui.dymatic.base.like.SchoolDymaticLikeContract
import com.daijie.ksyllabusapp.ui.dymatic.base.like.SchoolDymaticLikePresenter
import com.daijie.ksyllabusapp.ui.dymatic.base.like.SchoolDymaticLikeViewImpl
import com.daijie.ksyllabusapp.ui.dymatic.base.list.DymaticDialogHandler
import com.daijie.ksyllabusapp.ext.clipboard
import com.daijie.ksyllabusapp.ext.newDivLineItemDecoration
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.ext.toastW
import kotlinx.android.synthetic.main.activity_school_dymatic_detail.*
import kotlinx.android.synthetic.main.header_dymatic_detail.view.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/24.
 */
abstract class SchoolDymaticDetailActivity : BaseActivity(), EditCommentDialog.OnCommentSendListener,
        SchoolDymaticCommentContract.View, OnePageLoadDataViewProxy<SchoolDymaticCommentData>,
        LoadingViewProxy, SchoolDymaticDeleteContract.IView, DymaticDialogHandler.OnDymaticDialogSelectedListener {

    companion object {

        const val REQUEST_DETAIL = 200

        const val EXTRA_SCHOOL_DYMATIC_DATA = "SchoolDymaticData"

        const val EXTRA_SHOW_EDIT_DIALOG = "showEditDialog"

        const val EXTRA_UPDATED_DYMATIC_DATA = "updatedDymaticData"

        const val EXTRA_DYMATIC_DATA_POSITION = "dymaticDataPosition"

        const val RESULT_DELETE_DYMATIC = 404

        @JvmStatic
        fun newIntent(context: Context, schoolDymaticActivityClass: Class<out SchoolDymaticDetailActivity>,
                      schoolDymaticData: SchoolDymaticData, dymaticDataPosition: Int, showEditDialog: Boolean = false)
                = Intent(context, schoolDymaticActivityClass)
                .apply {
                    putExtra(EXTRA_SHOW_EDIT_DIALOG, showEditDialog)
                    putExtra(EXTRA_DYMATIC_DATA_POSITION, dymaticDataPosition)
                    putExtra(EXTRA_SCHOOL_DYMATIC_DATA, schoolDymaticData)
                }
    }

    override val contentViewId = R.layout.activity_school_dymatic_detail

    override var titleToolbar: Toolbar? = null
        get() = appToolbar
    override var titleText: TextView? = null
        get() = titleTextView

    override val loadingView: LoadingView by lazy {
        LoadingViewImpl(this)
    }

    @Inject
    lateinit var schoolDymaticiDeletePresenter: SchoolDymaticiDeletePresenter

    @Inject
    lateinit var schoolDymaticLikePresenter: SchoolDymaticLikePresenter

    val backIntent: Intent by lazy {
        Intent().apply {
            putExtra(EXTRA_DYMATIC_DATA_POSITION, intent.getIntExtra(EXTRA_DYMATIC_DATA_POSITION, -1))
        }
    }

    abstract val schoolDymaticCommentPresenter: SchoolDymaticCommentContract.Presenter

    abstract val schoolDymaticAdapter: SchoolDymaticAdapter<*>

    private var editCommentDialog: EditCommentDialog? = null

    protected val schoolDymaticData: SchoolDymaticData by lazy {
        intent.getParcelableExtra<SchoolDymaticData>(EXTRA_SCHOOL_DYMATIC_DATA)
    }

    private val schoolDymaticCommentAdapter: SchoolDymaticCommentAdapter by lazy {
        SchoolDymaticCommentAdapter(this)
    }

    private val headerView: View by lazy {
        layoutInflater.inflate(
                R.layout.header_dymatic_detail, commentRecyclerView.parent as ViewGroup, false
        )
    }

    private val showEditDialog: Boolean by lazy {
        intent.getBooleanExtra(EXTRA_SHOW_EDIT_DIALOG, false)
    }

    private val commentDialogHandler: CommentDialogHandler by lazy {
        CommentDialogHandler(this, schoolDymaticCommentPresenter,
                schoolDymaticCommentAdapter, schoolDymaticData) {
            showEditCommentDialog(it)
        }
    }

    protected val schoolDymaticDeleteViewProxy: SchoolDymaticDeleteViewProxy by lazy {
        SchoolDymaticDeleteViewProxy(this)
    }

    private val dymaticDialogHandler: DymaticDialogHandler by lazy {
        DymaticDialogHandler(this, this)
    }

    protected val schoolDymaticLikeView: SchoolDymaticLikeViewImpl by lazy {
        SchoolDymaticLikeViewImpl(this)
    }

    override val onePageLoadDataView: OnePageLoadDataViewImpl<SchoolDymaticCommentData> by lazy {
        OnePageLoadDataViewImpl(this, schoolDymaticCommentAdapter)
    }

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("消息详情")
        StoreProxy.onRestoreInstanceState(savedInstanceState, schoolDymaticCommentAdapter)
        initRecyclerView()
        initHeaderView()
        initInject()
        if (schoolDymaticCommentAdapter.isPre()) {
            letPresenterLoadData()
        }
        if (showEditDialog) {
            showEditCommentDialog()
        }
    }

    override fun onDestroy() {
        schoolDymaticiDeletePresenter.unsubscribe()
        schoolDymaticLikePresenter.unsubscribe()
        schoolDymaticCommentPresenter.unsubscribe()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        StoreProxy.onSaveInstanceState(outState, schoolDymaticCommentAdapter)
    }

    override fun onCommentSend(comment: String) {
        schoolDymaticCommentPresenter.sendComment(schoolDymaticData, comment)
    }

    override fun showSuccess(msg: String) {
        when (msg) {
            SchoolDymaticCommentContract.MESSAGE_COMMIT_SUCCESS -> {
                editCommentDialog?.dismiss()
                toastS(msg)
                letPresenterLoadData()
            }
            MESSAGE_SUCCESS_LOAD, MESSAGE_DELETE_COMMIT_SUCCESS -> {
                super.showSuccess(msg)
                resetComments()
            }
            SchoolDymaticLikeContract.MESSAGE_LIKE_SUCCESS, SchoolDymaticLikeContract.MESSAGE_UNLIKE_SUCCESS -> {
                resetLike()
            }
            SchoolDymaticDeleteContract.MESSAGE_DELETE_SCHOOL_DYMATIC_SUCCESS -> {
                toastS(SchoolDymaticDeleteContract.MESSAGE_DELETE_SCHOOL_DYMATIC_SUCCESS)
                finish()
            }
            else -> super.showSuccess(msg)
        }
    }


    override fun showError(msg: String) {
        when (msg) {
            SchoolDymaticCommentContract.MESSAGE_COMMIT_ERROR -> {
                editCommentDialog?.dismiss()
                toastW(msg)
            }
            else -> super.showError(msg)
        }
    }

    override fun addFailComment(commentData: SchoolDymaticCommentData) {
        schoolDymaticCommentAdapter.addFailComment(commentData)
    }

    override fun removeDymaticItem(position: Int) {
        setResult(RESULT_DELETE_DYMATIC, backIntent)
    }

    override fun replyDymatic(schoolDymaticData: SchoolDymaticData, adapterPosition: Int) {
        showEditCommentDialog()
    }

    override fun copyDymaticText(schoolDymaticData: SchoolDymaticData) {
        clipboard = schoolDymaticAdapter.getDymaticContent(schoolDymaticData)
    }

    override fun deleteDymatic(schoolDymaticData: SchoolDymaticData, adapterPosition: Int) {
        schoolDymaticiDeletePresenter.deleteDymatic(schoolDymaticData, adapterPosition)
    }

    abstract protected fun initInject()

    private fun initHeaderView() {
        headerView.schoolDymaticRecyclerView.layoutManager = LinearLayoutManager(this)
        headerView.schoolDymaticRecyclerView.adapter = schoolDymaticAdapter
        schoolDymaticAdapter.setNewData(listOf(schoolDymaticData))
        schoolDymaticAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.commentLayout -> {
                    showEditCommentDialog()
                }
            }
        }
        schoolDymaticAdapter.setOnItemLongClickListener { adapter, view, position ->
            dymaticDialogHandler.handleDymaticDialog(schoolDymaticData, position)
            true
        }
        schoolDymaticAdapter.setLikeStatusChangeListener { schoolDymaticData: SchoolDymaticData, isLike, likeButton, likeTextView ->
            schoolDymaticLikeView.handle(schoolDymaticData, likeButton, likeTextView, isLike)
        }
    }

    private fun showEditCommentDialog(otherCommentData: SchoolDymaticCommentData? = null) {
        editCommentDialog = EditCommentDialog.newInstance(otherCommentData == null,
                otherCommentData).apply {
            show(supportFragmentManager)
        }
    }

    private fun initRecyclerView() {
        schoolDymaticCommentAdapter.setHeaderAndEmpty(true)
        commentRecyclerView.layoutManager = LinearLayoutManager(this)
        schoolDymaticCommentAdapter.bindToRecyclerView(commentRecyclerView)
        commentRecyclerView.addItemDecoration(newDivLineItemDecoration(R.drawable.dev_line_full))
        schoolDymaticCommentAdapter.addHeaderView(headerView)
        schoolDymaticCommentAdapter.setOnErrorViewClickListener {
            letPresenterLoadData()
        }
        schoolDymaticCommentAdapter.setOnItemClickListener { adapter, view, position ->
            val commentData = schoolDymaticCommentAdapter.data[position]
            commentDialogHandler.handleCommentDialog(commentData, position, false)

        }
        schoolDymaticCommentAdapter.setOnItemLongClickListener { adapter, view, position ->
            val commentData = schoolDymaticCommentAdapter.data[position]
            commentDialogHandler.handleCommentDialog(commentData, position, true)
            true
        }
        schoolDymaticCommentAdapter.restoreState()
    }

    private fun letPresenterLoadData() {
        schoolDymaticCommentPresenter.loadComments(schoolDymaticData)
    }


    private fun resetComments() {
        schoolDymaticData.schoolNotice.comments = schoolDymaticCommentAdapter.data.asSequence()
                .filter {
                    it.isCommentSuccess == true
                }
                .map {
                    SchoolDymaticComments(it.schoolDymaticComment.uid, it.schoolDymaticComment.id)
                }.toList()
        backIntent.putExtra(EXTRA_UPDATED_DYMATIC_DATA, schoolDymaticData)
        setResult(Activity.RESULT_OK, backIntent)
        schoolDymaticAdapter.notifyDataSetChanged()
    }

    private fun resetLike() {
        backIntent.putExtra(EXTRA_UPDATED_DYMATIC_DATA, schoolDymaticData)
        setResult(Activity.RESULT_OK, backIntent)
    }
}