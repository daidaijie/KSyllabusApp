package com.daijie.ksyllabusapp.ui.syllabus.main

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.data.SemesterSyllabus

/**
 * Created by daidaijie on 17-9-28.
 */
interface SyllabusContract {

    interface Presenter : BasePresenter {
        fun getSyllabus(refresh: Boolean = true)
        var cacheSyllabus: SemesterSyllabus?
    }

    interface View : BaseView<Presenter>, ShowStatusView {
        fun setNickname(nickname: String)
        fun setSemesterText(semesterText: String)
    }
}