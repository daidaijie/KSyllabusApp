package com.daijie.ksyllabusapp.ui.selectlesson.addshare

import com.daijie.ksyllabusapp.base.*
import com.daijie.ksyllabusapp.data.LessonChoice

/**
 * Created by daidaijie on 2017/10/7.
 */
interface AddShareContract {

    interface Presenter : BasePresenter {
        fun addShareLessons(lessonChoices: List<LessonChoice>)
        fun getShareById(objectId: String)
    }

    interface View : BaseView<Presenter>, LoadingView, ShowStatusView, ToLoginView {
        fun showData(lessonChoices: List<LessonChoice>)
        fun handleSameAccount(yes: (() -> Unit)? = null, no: (() -> Unit)? = null)
    }
}