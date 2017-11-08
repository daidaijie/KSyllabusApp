package com.daijie.ksyllabusapp.adapter

import android.net.Uri
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.ext.dp2px
import com.daijie.ksyllabusapp.utils.DrawableTintUtils
import kotlinx.android.synthetic.main.item_photo_selected.view.*

/**
 * Created by daidaijie on 17-10-29.
 */
class SchoolDymaticPhotoSelectedAdapter()
    : BaseQuickStateAdapter<String, SchoolDymaticPhotoSelectedAdapter.ViewHolder>
(R.layout.item_photo_selected, mutableListOf("")) {

    fun addUris(uris: List<String>) {
        data.removeAt(data.size - 1)
        data.addAll(uris)
        if (data.size < 3) {
            data.add("")
        }
        notifyDataSetChanged()
    }

    fun getMaxSelectedNum() = if (data.size >= 3) 0 else 4 - data.size

    override fun convert(helper: ViewHolder, item: String) {
        helper.bindImage(item)
    }

    val realData: List<String>
        get() {
            return data.filter {
                it.isNotEmpty()
            }
        }

    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {
        fun bindImage(uri: String) {
            if (uri.isEmpty()) {
                mView.photoImageView.setImageDrawable(
                        DrawableTintUtils.getTintDrawableByColorRes(R.drawable.ic_add_white_24dp, R.color.colorTextDisable)
                )
                val dp20 = dp2px(20f)
                mView.photoImageView.setPadding(dp20, dp20, dp20, dp20)
            } else {
                mView.photoImageView.setPadding(0, 0, 0, 0)
                mView.photoImageView.setImageURI(Uri.parse(uri))
            }
        }
    }
}