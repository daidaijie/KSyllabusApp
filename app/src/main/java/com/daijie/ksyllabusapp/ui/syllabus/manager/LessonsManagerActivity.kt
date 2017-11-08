package com.daijie.ksyllabusapp.ui.syllabus.manager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.LessonManagerAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.ext.toastE
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import kotlinx.android.synthetic.main.activity_lesson_manager.*
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-9.
 */
class LessonsManagerActivity : BaseActivity(), LessonManagerContract.View {

    companion object {
        fun newIntent(context: Context) = Intent(context, LessonsManagerActivity::class.java)
    }

    @Inject
    lateinit var lessonManagerPresenter: LessonManagerPresenter

    override var titleToolbar: Toolbar? = null
        get() = appToolbar

    override val contentViewId = R.layout.activity_lesson_manager

    val creditLessonListFragment = LessonsDetailListFragment()
    val diyLessonListFragment = LessonsDetailListFragment()
    val lessonManagerAdapter: LessonManagerAdapter by lazy {
        LessonManagerAdapter(supportFragmentManager,
                arrayOf(creditLessonListFragment, diyLessonListFragment))
    }

    override fun initView(savedInstanceState: Bundle?) {
        lessonManagerViewPager.adapter = lessonManagerAdapter
        tabLayout.setupWithViewPager(lessonManagerViewPager)
        tabLayout.getTabAt(0)?.text = "学分制课程"
        tabLayout.getTabAt(1)?.text = "自定义课程"

        initInject()
        lessonManagerPresenter.subscribe()
    }

    private fun initInject() {
        DaggerLessonManagerComponent.builder()
                .userComponent(App.userComponent)
                .lessonManagerModule(LessonManagerModule(this))
                .build().inject(this)
    }

    override fun onDestroy() {
        lessonManagerPresenter.unsubscribe()
        super.onDestroy()
    }

    override fun showSuccess(msg: String) {
    }

    override fun showError(msg: String) {
        toastE(msg)
    }

    override fun showLessons(creditLessons: List<Lesson>?, diyLessons: List<Lesson>?) {
        creditLessonListFragment.showData(creditLessons)
        diyLessonListFragment.showData(diyLessons)
    }
}