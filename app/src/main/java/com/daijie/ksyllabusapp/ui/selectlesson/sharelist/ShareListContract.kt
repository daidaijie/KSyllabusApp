package com.daijie.ksyllabusapp.ui.selectlesson.sharelist

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.LoadingView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.repository.lesson.ShareLessonBaseInfo

/**
 * Created by daidaijie on 2017/10/7.
 */
interface ShareListContract {
    interface Presenter : BasePresenter {
        fun loadRecord()
        fun deleteRecord(objectsId: String, onDeleteSuccess: () -> Unit)
    }

    interface View : BaseView<Presenter>, ShowStatusView, LoadingView {
        fun showShareList(shareLessonBaseInfo: List<ShareLessonBaseInfo>)
    }
}