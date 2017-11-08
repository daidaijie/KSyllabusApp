package com.daijie.ksyllabusapp.ui.dymatic.circle.send

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.LoadingView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.littlevp.uploadimage.UploadImageContract

/**
 * Created by daidaijie on 17-10-29.
 */
interface SchoolCircleSendContract {

    companion object {
        const val MESSAGE_SEND_CIRCLE_SUCCESS = "消息圈发送成功"
        const val MESSAGE_SEND_CIRCLE_FAIL = "消息圈发送失败"
    }

    interface Presenter : BasePresenter {
        fun postCircle(content: String, imageJson: String, source: String)
    }

    interface View : BaseView<Presenter>, UploadImageContract.IView, ShowStatusView, LoadingView {

    }

}