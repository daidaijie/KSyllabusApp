package com.daijie.ksyllabusapp.data

import android.support.annotation.DrawableRes

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

/**
 * Created by liyujie on 2017/3/8.
 */

class ImageLoaderOptions {

    @DrawableRes
    var errorDrawable = NULL_DRAWABLE  //加载错误的时候显示的drawable

    @DrawableRes
    var defaultDrawable = NULL_DRAWABLE//加载中的时候显示的drawable

    var isShowAni = true

    var bitmapTransformation: BitmapTransformation? = null

    companion object {
        const val NULL_DRAWABLE = -1
    }
}
