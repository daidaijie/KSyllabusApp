package com.daijie.ksyllabusapp.ui.addlesson

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import android.widget.TextView
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.LessonTimeAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.base.LoadingViewImpl
import com.daijie.ksyllabusapp.base.ToLoginViewImpl
import com.daijie.ksyllabusapp.data.LessonTime
import com.daijie.ksyllabusapp.data.TimeRange
import com.daijie.ksyllabusapp.data.formatText
import com.daijie.ksyllabusapp.data.lessonWeekString
import com.daijie.ksyllabusapp.ext.toastE
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.widgets.LineEditLayout
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_add_lesson.*
import kotlinx.android.synthetic.main.footer_add_lesson.view.*
import kotlinx.android.synthetic.main.header_add_lesson.view.*
import kotlinx.android.synthetic.main.view_hint_edit_text.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-1.
 */
class AddLessonActivity : BaseActivity(), AddLessonContract.View {

    override var titleToolbar: Toolbar? = null
        get() = appToolbar

    override var titleText: TextView? = null
        get() = titleTextView

    val lessonTimeAdapter = LessonTimeAdapter()

    @Inject
    lateinit var addLessonPresenter: AddLessonPresenter

    private var loadingDialog = LoadingViewImpl(this)

    private val toLoginView = ToLoginViewImpl(this)

    private var mLesson: Lesson? = null

    companion object {
        const val EXTRA_LESSON = "lesson"
        @JvmStatic
        fun newIntent(context: Context, lesson: Lesson? = null)
                = Intent(context, AddLessonActivity::class.java)
                .apply {
                    if (lesson != null) {
                        putExtra(EXTRA_LESSON, lesson)
                    }
                }
    }

    override val contentViewId = R.layout.activity_add_lesson

    override fun initView(savedInstanceState: Bundle?) {
        initInject()
        initMenu()
        initRecyclerView()
        initHeaderAndFooter()
        mLesson = intent.getParcelableExtra(EXTRA_LESSON)
        setTitle(if (mLesson == null) "添加课程" else "修改课程")
        initLessonState()
    }

    private fun initLessonState() {
        mLesson?.let {
            with(lessonTimeAdapter.headerLayout.getChildAt(0)) {
                lessonNameEditLayout.editText.setText(it.name)
                lessonAddressEditLayout.editText.setText(it.room)
                lessonTeacherEditLayout.editText.setText(it.teacher)
                addLessonPresenter.parseLessonTimeToShow(it)
            }
        }
    }

    private fun initInject() {
        DaggerAddLessonComponent.builder()
                .userComponent(App.userComponent)
                .addLessonModule(AddLessonModule(this))
                .build().inject(this)
    }

    private fun initMenu() {
        appToolbar.inflateMenu(R.menu.menu_finish)
        appToolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_finish) {
                with(lessonTimeAdapter.headerLayout.getChildAt(0)) {
                    if (mLesson == null) {
                        addLessonPresenter.postLesson(
                                lessonNameEditLayout.editText.text.toString(),
                                lessonTeacherEditLayout.editText.text.toString(),
                                lessonAddressEditLayout.editText.text.toString(),
                                lessonTimeAdapter.data
                        )
                    } else {
                        addLessonPresenter.updateLesson(
                                lessonNameEditLayout.editText.text.toString(),
                                lessonTeacherEditLayout.editText.text.toString(),
                                lessonAddressEditLayout.editText.text.toString(),
                                lessonTimeAdapter.data, mLesson!!
                        )
                    }
                }
            }
            true
        }
    }

    private fun initRecyclerView() {
        addLessonRecyclerView.layoutManager = LinearLayoutManager(this)
        addLessonRecyclerView.adapter = lessonTimeAdapter
        lessonTimeAdapter.addData(LessonTime())
        lessonTimeAdapter.setOnItemChildClickListener { _, view, position ->
            when {
                view.id == R.id.removeLessonTimeImageView -> {
                    hintEditText.requestFocus()
                    lessonTimeAdapter.remove(position)
                }
            }
        }
        lessonTimeAdapter.setOnTimeSelectListener { lessonTime, adapterPosition ->
            val dialog = SelectTimeDialog.newInstance(lessonTime.timeRange)
            dialog.setOnTimeSelectedListener { date, startTime, endTime ->
                lessonTime.timeRange = TimeRange(date, startTime, endTime)
                (lessonTimeAdapter.getViewByPosition(
                        addLessonRecyclerView, adapterPosition, R.id.timeEditLayout
                ) as? LineEditLayout)?.editText?.setText(lessonTime.timeRange.formatText)
            }
            dialog.show(supportFragmentManager)
        }
        lessonTimeAdapter.setOnWeekSelectListener { lessonTime, adapterPosition ->
            val dialog = SelectWeekDialog.newInstance(lessonTime.weeks)
            dialog.setOnWeekSelectedListener { weeks ->
                lessonTime.weeks = weeks
                (lessonTimeAdapter.getViewByPosition(
                        addLessonRecyclerView, adapterPosition, R.id.weekEditLayout
                ) as? LineEditLayout)?.editText?.setText(lessonTime.lessonWeekString)
            }
            dialog.show(supportFragmentManager)
        }
    }

    private fun initHeaderAndFooter() {
        val header = layoutInflater
                .inflate(
                        R.layout.header_add_lesson, addLessonRecyclerView.parent as ViewGroup, false
                ) as ViewGroup

        lessonTimeAdapter.addHeaderView(header)

        val footer = layoutInflater
                .inflate(
                        R.layout.footer_add_lesson, addLessonRecyclerView.parent as ViewGroup, false
                ) as ViewGroup
        lessonTimeAdapter.addFooterView(footer)
        footer.addLessonTimeLayout.setOnClickListener {
            if (lessonTimeAdapter.data.size >= 1) {
                lessonTimeAdapter.addData(lessonTimeAdapter.data[lessonTimeAdapter.data.size - 1].copy(
                        timeRange = TimeRange()
                ))
            } else {
                lessonTimeAdapter.addData(LessonTime())
            }
        }
    }

    override fun showLessonTime(lessonTimes: MutableList<LessonTime>) {
        Logger.e(lessonTimes.toString())
        lessonTimeAdapter.setNewData(lessonTimes)
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

    override fun toLoginAct() {
        toLoginView.toLoginAct()
    }
}