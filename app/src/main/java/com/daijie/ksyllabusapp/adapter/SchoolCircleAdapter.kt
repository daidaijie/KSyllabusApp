package com.daijie.ksyllabusapp.adapter

import android.content.Context
import android.view.View
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.data.ImageLoaderOptions
import com.daijie.ksyllabusapp.data.SchoolDymaticData
import com.daijie.ksyllabusapp.utils.CircleTransform
import com.daijie.ksyllabusapp.utils.ImageLoader
import kotlinx.android.synthetic.main.item_school_dymatic_content.view.*

/**
 * Created by daidaijie on 2017/10/22.
 */
class SchoolCircleAdapter(context: Context) : SchoolDymaticAdapter<SchoolCircleAdapter.ViewHolder>(context) {

    override fun getDymaticContent(schoolDymaticData: SchoolDymaticData) = schoolDymaticData.schoolNotice.content

    inner class ViewHolder(mView: View) : SchoolDymaticAdapter.SchoolDymaticViewHolder(mView) {
        override fun afterBaseBindSchoolDymaticData(schoolDymaticData: SchoolDymaticData) {
            // 隐藏不需要的布局
            mView.linkLayout.visibility = View.GONE
            mView.headTextView.visibility = View.GONE

            // 设置头像
            val options = ImageLoaderOptions().apply {
                errorDrawable = R.drawable.bg_circle_disable
                defaultDrawable = R.drawable.bg_circle_disable
                bitmapTransformation = CircleTransform(context)
            }
            if (schoolNotice.schoolDymaticUser.id == -1) {
                mView.headImageView.setImageResource(R.drawable.ic_anonymous_headimg)
            } else {
                schoolNotice.schoolDymaticUser.image.let {
                    if (it == null || it.isBlank()) {
                        mView.headImageView.setImageResource(R.drawable.ic_default_headimg)
                    } else {
                        ImageLoader.loadImage(mView.headImageView, it, options)
                    }
                }
            }
        }

        override fun author() = schoolNotice.schoolDymaticUser.name

        override fun source() = schoolNotice.source ?: "旧版"

        override fun content() = getDymaticContent(schoolDymaticData)
    }
}