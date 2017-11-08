package com.daijie.ksyllabusapp.ui.login

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.LoadingView
import com.daijie.ksyllabusapp.base.ShowStatusView

/**
 * Created by daidaijie on 17-9-28.
 */
interface LoginContract {
    interface Presenter : BasePresenter {
        fun login(username: String, password: String)
    }

    interface View : BaseView<Presenter>, LoadingView, ShowStatusView {
        fun setUsername(username: String)
        fun setPassword(password: String)
    }
}