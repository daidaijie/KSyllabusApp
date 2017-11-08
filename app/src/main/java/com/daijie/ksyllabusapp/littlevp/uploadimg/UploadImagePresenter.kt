package com.daijie.ksyllabusapp.littlevp.uploadimage

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.ext.to_io
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.lcimage.LCImageDataSourceRepository
import com.orhanobut.logger.Logger
import rx.Observable
import java.io.File
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-29.
 */
class UploadImagePresenter @Inject constructor(
        private val uploadImageView: UploadImageContract.View,
        private val lcImageDataSourceRepository: LCImageDataSourceRepository,
        private val subscriptioner: Subscriptioner
) : UploadImageContract.Presenter, ISubscriptioner by subscriptioner {

    @Inject
    fun setPrsenter() {
        uploadImageView.presenter = this
    }

    override fun uploadImages(images: List<String>) {
//        uploadImageView.showLoadingDialog("正在上传图片")
        Observable.from(images)
                .map {
                    val file = File(it.substring("file:".length, it.length))
                    file
                }
                .to_io()
                .flatMap {
                    return@flatMap lcImageDataSourceRepository.uploadImage(it)
                }
                .toList()
                .to_ui()
                .subscribe({
                    uploadImageView.showSuccess(UploadImageContract.MESSAGE_UPLOAD_SUCCESS)
                    uploadImageView.onSuccess(it)
//                    uploadImageView.hideLoadingDialog()
                }, {
                    uploadImageView.showError(UploadImageContract.MESSAGE_UPLOAD_FAIL)
//                    uploadImageView.hideLoadingDialog()
                })
                .let {
                    add(it)
                }
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        dispose()
    }
}