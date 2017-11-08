package com.daijie.ksyllabusapp.ui.selectlesson.sharelist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import android.widget.TextView
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.ShareListAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.base.LoadingViewImpl
import com.daijie.ksyllabusapp.ext.getDrawableById
import com.daijie.ksyllabusapp.ext.toastE
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.repository.lesson.ShareLessonBaseInfo
import com.daijie.ksyllabusapp.ui.selectlesson.share.ShareLessonActivity
import com.daijie.ksyllabusapp.widgets.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_share_list.*
import kotlinx.android.synthetic.main.empty_normal.view.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by daidaijie on 2017/10/7.
 */
class ShareListActivity : BaseActivity(), ShareListContract.View {

    companion object {
        const val REQUSEST_SHARE = 200

        @JvmStatic
        fun newIntent(context: Context) = Intent(context, ShareListActivity::class.java)
    }

    override var titleToolbar: Toolbar? = null
        get() = appToolbar
    override var titleText: TextView? = null
        get() = titleTextView

    private var loadingDialog = LoadingViewImpl(this)

    @Inject
    lateinit var shareListPresenter: ShareListPresenter

    private var shareListAdapter: ShareListAdapter by Delegates.notNull()

    override val contentViewId = R.layout.activity_share_list

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("分享课程")
        initMenu()
        initRecyclerView()
        initInject()
        shareListPresenter.subscribe()
    }

    override fun onDestroy() {
        shareListPresenter.unsubscribe()
        super.onDestroy()
    }

    private fun initMenu() {
        appToolbar.inflateMenu(R.menu.menu_add)
        appToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_add) {
                startActivityForResult(ShareLessonActivity.newIntent(this@ShareListActivity), REQUSEST_SHARE)
            }
            true
        }
    }

    private fun initRecyclerView() {
        shareListRecyclerView.layoutManager = LinearLayoutManager(this)
        shareListAdapter = ShareListAdapter()
        shareListAdapter.setHeaderAndEmpty(true)
        shareListRecyclerView.adapter = shareListAdapter
        val itemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                getDrawableById(R.drawable.dev_line_padding16)
        )
        itemDecoration.setHeight(1)
        shareListRecyclerView.addItemDecoration(itemDecoration)

        val header = layoutInflater
                .inflate(
                        R.layout.header_share_list, shareListRecyclerView.parent as ViewGroup, false
                ) as ViewGroup

        shareListAdapter.addHeaderView(header)
        val emptyView = layoutInflater
                .inflate(
                        R.layout.empty_normal, shareListRecyclerView.parent as ViewGroup, false
                ) as ViewGroup
        emptyView.emptyTextView.text = "还没分享记录\n点击右上角+号图标分享吧"
        shareListAdapter.emptyView = emptyView

        shareListAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.deleteButton -> showDeleteDialog {
                    shareListPresenter.deleteRecord(shareListAdapter.data[position].objectId) {
                        shareListAdapter.remove(position)
                        toastS("删除成功")
                    }
                }
            }
        }
    }

    private fun showDeleteDialog(callback: () -> Unit) {
        AlertDialog.Builder(this)
                .setTitle("警告")
                .setMessage("确定删除该分享记录？\n(PS:删除后其他人不能使用该分享码添加课程)")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", { _, _ ->
                    callback()
                }).create().show()
    }

    private fun initInject() {
        DaggerShareListComponent.builder()
                .userComponent(App.userComponent)
                .shareListModule(ShareListModule(this))
                .build().inject(this)
    }

    override fun showSuccess(msg: String) {
        toastS(msg)
    }

    override fun showError(msg: String) {
        toastE(msg)
    }

    override fun showShareList(shareLessonBaseInfo: List<ShareLessonBaseInfo>) {
        shareListAdapter.setNewData(shareLessonBaseInfo)
        shareListAdapter.notifyDataSetChanged()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUSEST_SHARE && resultCode == Activity.RESULT_OK) {
            shareListPresenter.loadRecord()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showLoadingDialog(msg: String?) {
        loadingDialog.showLoadingDialog(msg)
    }

    override fun hideLoadingDialog() {
        loadingDialog.hideLoadingDialog()
    }

}