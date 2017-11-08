package com.daijie.ksyllabusapp.ui.dymatic.base.like

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.data.SchoolNotice

/**
 * Created by daidaijie on 17-10-27.
 */
interface SchoolDymaticLikeContract {

    companion object {
        const val MESSAGE_LIKE_SUCCESS = "点赞成功"
        const val MESSAGE_LIKE_ERROR = "点赞失败"
        const val MESSAGE_UNLIKE_SUCCESS = "取消点赞成功"
        const val MESSAGE_UNLIKE_ERROR = "取消点赞失败"
    }

    interface Presenter : BasePresenter {
        fun like(schoolNotice: SchoolNotice, callback: (isSuccess: Boolean) -> Unit)
        fun unlike(schoolNotice: SchoolNotice, callback: (isSuccess: Boolean) -> Unit)
    }

    interface View : ShowStatusView, BaseView<Presenter> {
        var presenter: Presenter
    }
}