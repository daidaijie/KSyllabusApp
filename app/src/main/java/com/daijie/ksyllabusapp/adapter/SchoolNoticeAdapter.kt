package com.daijie.ksyllabusapp.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.data.SchoolDymaticData
import com.daijie.ksyllabusapp.ext.getColorById
import com.daijie.ksyllabusapp.ext.getDrawableById
import com.daijie.ksyllabusapp.ext.sp2px
import com.daijie.ksyllabusapp.utils.TimeFormatUtils
import com.daijie.ksyllabusapp.widgets.CustomLoadMoreView
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.item_school_dymatic_content.view.*

/**
 * Created by daidaijie on 2017/10/22.
 */
class SchoolNoticeAdapter(context: Context)
    : SchoolDymaticAdapter<SchoolNoticeAdapter.ViewHolder>(context) {

    override fun getDymaticContent(schoolDymaticData: SchoolDymaticData) = schoolDymaticData.schoolNotice.description

    inner class ViewHolder(mView: View)
        : SchoolDymaticAdapter.SchoolDymaticViewHolder(mView) {
        override fun afterBaseBindSchoolDymaticData(schoolDymaticData: SchoolDymaticData) {
            mView.headImageView.visibility = View.GONE
            if (schoolNotice.content.isNullOrBlank()) {
                mView.linkLayout.visibility = View.GONE
            } else {
                mView.linkLayout.visibility = View.VISIBLE
            }

            val icon = getDrawableById(R.drawable.bg_circle)
            mView.headTextView.background = icon
            mView.headTextView.text = schoolNotice.source.run {
                if (length > 3) {
                    substring(0, 3)
                } else {
                    this
                }
            }
        }

        override fun author() = schoolNotice.source

        override fun source() = ""

        override fun content() = getDymaticContent(schoolDymaticData)
    }
}