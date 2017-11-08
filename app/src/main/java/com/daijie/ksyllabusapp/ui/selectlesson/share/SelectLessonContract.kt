package com.daijie.ksyllabusapp.ui.selectlesson.share

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.LoadingView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.data.LessonChoice

/**
 * Created by daidaijie on 2017/10/6.
 */
interface SelectLessonContract {

    interface Presenter : BasePresenter {
        fun shareLessons(lessonChoices: List<LessonChoice>)
    }

    interface View : BaseView<Presenter>, ShowStatusView, LoadingView {
        fun showLessonList(lessonChoices: List<LessonChoice>)
    }
}