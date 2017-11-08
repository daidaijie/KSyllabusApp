package com.daijie.ksyllabusapp.ui.syllabus.lesson

import com.daijie.ksyllabusapp.base.*
import com.daijie.ksyllabusapp.repository.lesson.Lesson

/**
 * Created by daidaijie on 17-10-13.
 */
interface LessonDetailContract {
    interface Presenter : BasePresenter {
        fun deleteLesson(lesson: Lesson)
    }

    interface View : BaseView<Presenter>, ShowStatusView, LoadingView, ToLoginView {
    }
}