package com.daijie.ksyllabusapp.ui.addlesson

import com.daijie.ksyllabusapp.base.*
import com.daijie.ksyllabusapp.data.LessonTime
import com.daijie.ksyllabusapp.repository.lesson.Lesson

/**
 * Created by daidaijie on 2017/10/5.
 */
interface AddLessonContract {
    interface Presenter : BasePresenter {
        fun postLesson(lessonName: String, lessonTeacher: String, lessonAddress: String,
                       lessonTimes: List<LessonTime>)

        fun parseLessonTimeToShow(lesson: Lesson)

        fun updateLesson(lessonName: String, lessonTeacher: String, lessonAddress: String,
                         lessonTimes: List<LessonTime>, oldLesson: Lesson)
    }

    interface View : BaseView<Presenter>, LoadingView, ShowStatusView, ToLoginView {
        fun showLessonTime(lessonTimes: MutableList<LessonTime>)
    }
}