package com.daijie.ksyllabusapp.utils

import android.app.Activity
import android.support.v4.app.Fragment
import android.widget.ImageView

import com.bumptech.glide.DrawableTypeRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.daijie.ksyllabusapp.data.ImageLoaderOptions

/**
 * Created by liyujie on 2017/3/8.
 */

object ImageLoader {

    fun loadImage(imageView: ImageView, url: String, options: ImageLoaderOptions? = null) {
        loadImage(null, null, imageView, url, options)
    }

    fun loadImage(fragment: Fragment, imageView: ImageView, url: String, options: ImageLoaderOptions? = null) {
        loadImage(null, fragment, imageView, url, options)
    }

    fun loadImage(activity: Activity, imageView: ImageView, url: String, options: ImageLoaderOptions? = null) {
        loadImage(activity, null, imageView, url, options)
    }

    private fun loadImage(activity: Activity?, fragment: Fragment?, imageView: ImageView, url: String, options: ImageLoaderOptions?) {
        val manager: RequestManager
        if (activity != null) {
            manager = Glide.with(activity)
        } else if (fragment != null) {
            manager = Glide.with(fragment)
        } else {
            manager = Glide.with(imageView.context)
        }
        val request = manager.load(url)

        if (options != null) {
            if (options.defaultDrawable != ImageLoaderOptions.NULL_DRAWABLE) {
                request.placeholder(options.defaultDrawable)
            }
            if (options.errorDrawable != ImageLoaderOptions.NULL_DRAWABLE) {
                request.error(options.errorDrawable)
            }
            if (options.bitmapTransformation != null) {
                request.transform(options.bitmapTransformation)
            }
            if (!options.isShowAni) {
                request.dontAnimate()
            }
        }

        request.into(imageView)
    }
}
