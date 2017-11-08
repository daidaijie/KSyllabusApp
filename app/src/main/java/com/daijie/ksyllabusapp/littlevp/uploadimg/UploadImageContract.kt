package com.daijie.ksyllabusapp.littlevp.uploadimage

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.LoadingView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.data.LCFile

/**
 * Created by daidaijie on 17-10-29.
 */

interface UploadImageContract {

    companion object {
        const val MESSAGE_UPLOAD_SUCCESS = "上传图片成功"
        const val MESSAGE_UPLOAD_FAIL = "上传图片失败"
    }

    interface Presenter : BasePresenter {
        fun uploadImages(images: List<String>)
    }

    interface View : BaseView<Presenter>, IView {
        var presenter: Presenter
    }

    interface IView : ShowStatusView, LoadingView {
        fun onSuccess(lcFiles: List<LCFile>)
    }
}
