package com.daijie.ksyllabusapp.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.daijie.ksyllabusapp.ui.photo.PhotoPagerActivity
import com.jaeger.ninegridimageview.NineGridImageViewAdapter

/**
 * Created by daidaijie on 17-10-22.
 */
class SchoolDymaticGridImageViewAdapter : NineGridImageViewAdapter<String>() {

    override fun onDisplayImage(context: Context, imageView: ImageView, t: String) {
        Glide.with(context).load(t).into(imageView)
    }

    override fun onItemImageClick(context: Context, index: Int, list: MutableList<String>) {
        context.startActivity(PhotoPagerActivity.newIntent<PhotoPagerActivity>(context, list.toTypedArray(), index))
    }
}