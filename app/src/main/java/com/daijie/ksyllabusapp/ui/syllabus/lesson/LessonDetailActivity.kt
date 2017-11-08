package com.daijie.ksyllabusapp.ui.syllabus.lesson

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.ui.addlesson.AddLessonActivity
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.base.LoadingViewImpl
import com.daijie.ksyllabusapp.base.ToLoginViewImpl
import com.daijie.ksyllabusapp.ext.toastE
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import kotlinx.android.synthetic.main.activity_lesson_detail.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/8.
 */
class LessonDetailActivity : BaseActivity(), LessonDetailContract.View {

    companion object {
        const val EXTRA_LESSON = "lesson"

        @JvmStatic
        fun newIntent(context: Context, lesson: Lesson) = Intent(context, LessonDetailActivity::class.java)
                .apply {
                    putExtra(EXTRA_LESSON, lesson)
                }
    }

    @Inject
    lateinit var lessonDetailPresenter: LessonDetailPresenter

    override var titleToolbar: Toolbar? = null
        get() = appToolbar
    override var titleText: TextView? = null
        get() = titleTextView

    private val toLoginView = ToLoginViewImpl(this)

    private var loadingDialog = LoadingViewImpl(this)

    override val contentViewId = R.layout.activity_lesson_detail

    private val lesson: Lesson by lazy {
        intent.getParcelableExtra<Lesson>(EXTRA_LESSON)
    }

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("课程详情")
        initInject()
        initInfo()
    }

    override fun onDestroy() {
        lessonDetailPresenter.unsubscribe()
        super.onDestroy()
    }

    private fun initInject() {
        DaggerLessonDetailComponent.builder()
                .userComponent(App.userComponent)
                .lessonDetailModule(LessonDetailModule(this))
                .build().inject(this)
    }

    private fun initInfo() {
        lessonNameTextLayout.setInfo(lesson.simpleName)
        lessonTeacherTextLayout.setInfo(lesson.teacher)
        lessonAddressTextLayout.setInfo(lesson.room)
        if (lesson.fromCreditSystem) {
            lessonIdTextLayout.visibility = View.VISIBLE
            lessonIdTextLayout.setInfo(lesson.id)
        } else {
            lessonIdTextLayout.visibility = View.GONE
        }

        if (lesson.fromCreditSystem) {
            lessonCreditTextLayout.visibility = View.VISIBLE
            lessonCreditTextLayout.setInfo(lesson.credit)
        } else {
            lessonCreditTextLayout.visibility = View.GONE
        }

        if (!lesson.fromCreditSystem) {
            deleteButton.visibility = View.VISIBLE
            deleteButton.setOnClickListener {
                lessonDetailPresenter.deleteLesson(lesson)
            }
            updateButton.visibility = View.VISIBLE
            updateButton.setOnClickListener {
                this@LessonDetailActivity.startActivity(
                        AddLessonActivity.newIntent(this, lesson)
                )
            }
        } else {
            updateButton.visibility = View.GONE
            deleteButton.visibility = View.GONE
        }
        lessonTimeTextLayout.setInfo(lesson.lessonTime)
    }

    override fun showLoadingDialog(msg: String?) {
        loadingDialog.showLoadingDialog(msg)
    }

    override fun hideLoadingDialog() {
        loadingDialog.hideLoadingDialog()
    }

    override fun showSuccess(msg: String) {
        toastS(msg)
    }

    override fun showError(msg: String) {
        toastE(msg)
    }

    override fun toLoginAct() {
        toLoginView.toLoginAct()
    }
}