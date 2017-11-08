package com.daijie.ksyllabusapp.ui.selectlesson.sendsyllabus

import com.daijie.ksyllabusapp.base.*
import com.daijie.ksyllabusapp.data.LessonChoice

/**
 * Created by daidaijie on 2017/10/15.
 */
interface SendSyllabusContract {

    interface Presenter : BasePresenter {
        fun sendLessons(collectionId: String, lessonChoices: List<LessonChoice>)
    }

    interface View : BaseView<Presenter>, ShowStatusView, LoadingView, ToLoginView {
        fun showLessonChoice(lessonChoices: List<LessonChoice>)
    }
}