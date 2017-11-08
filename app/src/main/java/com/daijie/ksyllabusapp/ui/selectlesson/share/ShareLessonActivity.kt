package com.daijie.ksyllabusapp.ui.selectlesson.share

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.LessonListAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.base.LoadingViewImpl
import com.daijie.ksyllabusapp.data.LessonChoice
import com.daijie.ksyllabusapp.ext.getDrawableById
import com.daijie.ksyllabusapp.ext.toastE
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.widgets.DividerItemDecoration
import kotlinx.android.synthetic.main.view_select_lesson.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/6.
 */
class ShareLessonActivity : BaseActivity(), SelectLessonContract.View {

    companion object {
        @JvmStatic
        fun newIntent(context: Context) = Intent(context, ShareLessonActivity::class.java)
    }

    private val lessonListAdapter = LessonListAdapter()

    @Inject
    lateinit var selectLessonPresenter: SelectLessonPresenter

    override var titleToolbar: Toolbar? = null
        get() = appToolbar

    override var titleText: TextView? = null
        get() = titleTextView

    private var loadingDialog = LoadingViewImpl(this)

    override val contentViewId = R.layout.activity_share_lesson

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("选择你要分享的课程")
        initMenu()
        initRecyclerView()
        initInject()
        selectLessonPresenter.subscribe()
    }

    override fun onDestroy() {
        selectLessonPresenter.unsubscribe()
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

    private fun initMenu() {
        appToolbar.inflateMenu(R.menu.menu_finish)
        appToolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_finish) {
                selectLessonPresenter.shareLessons(lessonListAdapter.data)
            }
            true
        }
    }

    private fun initInject() {
        DaggerSelectLessonComponent.builder()
                .userComponent(App.userComponent)
                .selectLessonModule(SelectLessonModule(this))
                .build().inject(this)
    }

    override fun showLessonList(lessonChoices: List<LessonChoice>) {
        lessonListAdapter.setNewData(lessonChoices)
    }

    override fun showSuccess(msg: String) {
        toastS(msg)
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showError(msg: String) {
        toastE(msg)
    }

    override fun showLoadingDialog(msg: String?) {
        loadingDialog.showLoadingDialog(msg)
    }

    override fun hideLoadingDialog() {
        loadingDialog.hideLoadingDialog()
    }

}