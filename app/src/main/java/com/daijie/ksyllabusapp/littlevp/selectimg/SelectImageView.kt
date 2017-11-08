package com.daijie.ksyllabusapp.littlevp.selectimg

import android.content.Intent

/**
 * Created by daidaijie on 2017/11/7.
 * 复用两个发布动态的代码
 */
interface SelectImageView {
    fun pickImages()
    fun onImagePickupOrDelete(requestCode: Int, resultCode: Int, data: Intent?)
}