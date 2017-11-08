package com.daijie.ksyllabusapp.ui.oa.detail

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.data.OaFile

/**
 * Created by daidaijie on 17-10-21.
 */
interface OADetailContract {

    interface Presenter : BasePresenter {
        fun loadOaFiles()
    }

    interface View : BaseView<Presenter>, ShowStatusView {
        fun startLoading()
        fun showContent(context: String)
        fun showFiles(oaFiles: List<OaFile>)
    }
}