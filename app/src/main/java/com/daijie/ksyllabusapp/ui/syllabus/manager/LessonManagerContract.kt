package com.daijie.ksyllabusapp.ui.syllabus.manager

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.repository.lesson.Lesson

/**
 * Created by daidaijie on 17-10-10.
 */
interface LessonManagerContract {
    interface Presenter : BasePresenter {

    }

    interface View : BaseView<Presenter>, ShowStatusView {
        fun showLessons(creditLessons: List<Lesson>?, diyLessons: List<Lesson>?)
    }
}