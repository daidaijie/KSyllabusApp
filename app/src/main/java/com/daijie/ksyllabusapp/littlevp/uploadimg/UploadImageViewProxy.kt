package com.daijie.ksyllabusapp.littlevp.uploadimage

import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-29.
 */
class UploadImageViewProxy(
        private val uploadImageIView: UploadImageContract.IView
) : UploadImageContract.View, UploadImageContract.IView by uploadImageIView {

    @Inject
    override lateinit var presenter: UploadImageContract.Presenter

}