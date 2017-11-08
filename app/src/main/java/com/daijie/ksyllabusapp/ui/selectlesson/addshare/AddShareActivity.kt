package com.daijie.ksyllabusapp.ui.selectlesson.addshare

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.widget.TextView
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.LessonListAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.base.LoadingViewImpl
import com.daijie.ksyllabusapp.base.ToLoginViewImpl
import com.daijie.ksyllabusapp.data.LessonChoice
import com.daijie.ksyllabusapp.ext.clipboard
import com.daijie.ksyllabusapp.ext.getDrawableById
import com.daijie.ksyllabusapp.ext.toastE
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.widgets.DividerItemDecoration
import kotlinx.android.synthetic.main.dialog_share_code.view.*
import kotlinx.android.synthetic.main.view_select_lesson.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by daidaijie on 2017/10/7.
 */
class AddShareActivity : BaseActivity(), AddShareContract.View {

    companion object {
        fun newIntent(context: Context) = Intent(context, AddShareActivity::class.java)
    }

    private var shareCodeDialog: AlertDialog by Delegates.notNull()

    private var loadingDialog = LoadingViewImpl(this)

    @Inject
    lateinit var addSharePresenter: AddSharePresenter

    private var lessonListAdapter: LessonListAdapter by Delegates.notNull()

    private val toLoginView = ToLoginViewImpl(this)

    override var titleToolbar: Toolbar? = null
        get() = appToolbar

    override var titleText: TextView? = null
        get() = titleTextView

    override val contentViewId = R.layout.activity_lesson_list

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("选择要添加的课程")
        initMenu()
        initRecyclerView()
        initInject()
        addSharePresenter.subscribe()
        showInputDialog()
    }

    private fun initMenu() {
        appToolbar.inflateMenu(R.menu.menu_finish)
        appToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_finish) {
                addSharePresenter.addShareLessons(lessonListAdapter.data)
            }
            true
        }
    }

    private fun showInputDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_share_code, null, false)
        view.pasteButton.setOnClickListener {
            view.shareCodeEditText.setText(clipboard)
        }
        shareCodeDialog = AlertDialog.Builder(this)
                .setTitle("请输入分享码")
                .setView(view)
                .setPositiveButton("确定", null)
                .setNegativeButton("退出", { _, _ ->
                    this@AddShareActivity.finish()
                })
                .setCancelable(true)
                .setOnCancelListener {
                    this@AddShareActivity.finish()
                }
                .create()
        shareCodeDialog.setCanceledOnTouchOutside(false)
        shareCodeDialog.show()
        shareCodeDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener {
                    val code = view.shareCodeEditText.text.toString()
                    if (code.isBlank()) {
                        view.shareCodeTextInputLayout.error = "输入不能为空"
                    } else {
                        addSharePresenter.getShareById(code)
                    }
                }
    }

    private fun initRecyclerView() {
        selectLessonRecyclerView.layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                getDrawableById(R.drawable.dev_line_padding16)
        )
        itemDecoration.setHeight(1)
        selectLessonRecyclerView.addItemDecoration(itemDecoration)
        lessonListAdapter = LessonListAdapter()
        selectLessonRecyclerView.adapter = lessonListAdapter
    }

    override fun onDestroy() {
        addSharePresenter.unsubscribe()
        super.onDestroy()
    }

    private fun initInject() {
        DaggerAddShareComponent.builder()
                .userComponent(App.userComponent)
                .addShareModule(AddShareModule(this))
                .build().inject(this)
    }

    override fun showLoadingDialog(msg: String?) {
        loadingDialog.showLoadingDialog(msg)
    }

    override fun hideLoadingDialog() {
        loadingDialog.hideLoadingDialog()
    }

    override fun showSuccess(msg: String) {
        toastS(msg)
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showError(msg: String) {
        toastE(msg)
    }

    override fun showData(lessonChoices: List<LessonChoice>) {
        shareCodeDialog.dismiss()
        lessonListAdapter.setNewData(lessonChoices)
    }

    override fun handleSameAccount(yes: (() -> Unit)?, no: (() -> Unit)?) {
        AlertDialog.Builder(this)
                .setMessage("这是本账号分享出去的课程，确定要添加？\n(PS:将有可能与本身已有的课程发生冲突)")
                .setPositiveButton("确定", { _, _ ->
                    yes?.invoke()
                })
                .setNegativeButton("取消", { _, _ ->
                    no?.invoke()
                })
                .create().show()
    }

    override fun toLoginAct() {
        toLoginView.toLoginAct()
    }

}