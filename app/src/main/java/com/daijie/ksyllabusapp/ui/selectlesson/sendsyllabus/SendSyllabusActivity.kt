package com.daijie.ksyllabusapp.ui.selectlesson.sendsyllabus

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
 * Created by daidaijie on 2017/10/15.
 */
class SendSyllabusActivity : BaseActivity(), SendSyllabusContract.View {

    companion object {
        fun newIntent(context: Context) = Intent(context, SendSyllabusActivity::class.java)
    }

    private var shareCodeDialog: AlertDialog by Delegates.notNull()

    private val loadingDialog = LoadingViewImpl(this)

    private val toLoginView = ToLoginViewImpl(this)

    @Inject
    lateinit var sendSyllabusPresenter: SendSyllabusPresenter

    private val lessonListAdapter = LessonListAdapter()

    override var titleToolbar: Toolbar? = null
        get() = appToolbar
    override var titleText: TextView? = null
        get() = titleTextView

    override val contentViewId = R.layout.activity_share_lesson

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("发送课表")
        initMenu()
        initRecyclerView()
        initInject()
        sendSyllabusPresenter.subscribe()
    }

    private fun initMenu() {
        appToolbar.inflateMenu(R.menu.menu_finish)
        appToolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_finish) {
                showInputDialog()
            }
            true
        }
    }

    private fun initInject() {
        DaggerSendSyllabusComponent.builder()
                .userComponent(App.userComponent)
                .sendSyllabusModule(SendSyllabusModule(this))
                .build().inject(this)
    }

    override fun onDestroy() {
        sendSyllabusPresenter.unsubscribe()
        super.onDestroy()
    }

    private fun initRecyclerView() {
        selectLessonRecyclerView.layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                getDrawableById(R.drawable.dev_line_padding16)
        )
        itemDecoration.setHeight(1)
        selectLessonRecyclerView.addItemDecoration(itemDecoration)
        selectLessonRecyclerView.adapter = lessonListAdapter
    }

    override fun showSuccess(msg: String) {
        toastS(msg)
        if (msg == "上传成功") {
            shareCodeDialog.dismiss()
            finish()
        }
    }

    override fun showError(msg: String) {
        toastE(msg)
    }

    private fun showInputDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_share_code, null, false)
        view.pasteButton.setOnClickListener {
            view.shareCodeEditText.setText(clipboard)
        }
        view.shareCodeTextInputLayout.hint = "收集码"
        shareCodeDialog = AlertDialog.Builder(this)
                .setTitle("请输入收集码")
                .setView(view)
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .setCancelable(true)
                .setOnCancelListener {
                    this@SendSyllabusActivity.finish()
                }
                .create()
        shareCodeDialog.show()
        shareCodeDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener {
                    val code = view.shareCodeEditText.text.toString()
                    if (code.isBlank()) {
                        view.shareCodeTextInputLayout.error = "输入不能为空"
                    } else {
                        sendSyllabusPresenter.sendLessons(code, lessonListAdapter.data)
                    }
                }
    }


    override fun showLessonChoice(lessonChoices: List<LessonChoice>) {
        lessonListAdapter.setNewData(lessonChoices)
    }

    override fun showLoadingDialog(msg: String?) {
        loadingDialog.showLoadingDialog(msg)
    }

    override fun hideLoadingDialog() {
        loadingDialog.hideLoadingDialog()
    }

    override fun toLoginAct() {
        toLoginView.toLoginAct()
    }

}