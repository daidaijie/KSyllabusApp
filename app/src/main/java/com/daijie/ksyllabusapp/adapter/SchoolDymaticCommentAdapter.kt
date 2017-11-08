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
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.data.ImageLoaderOptions
import com.daijie.ksyllabusapp.data.SchoolDymaticCommentData
import com.daijie.ksyllabusapp.data.name
import com.daijie.ksyllabusapp.ext.getColorById
import com.daijie.ksyllabusapp.ext.sp2px
import com.daijie.ksyllabusapp.utils.CircleTransform
import com.daijie.ksyllabusapp.utils.ImageLoader
import com.daijie.ksyllabusapp.utils.TimeFormatUtils
import kotlinx.android.synthetic.main.item_school_dymatic_comment.view.*
import java.util.*

/**
 * Created by daidaijie on 2017/10/24.
 */
class SchoolDymaticCommentAdapter(val context: Context)
    : BaseQuickStateAdapter<SchoolDymaticCommentData, SchoolDymaticCommentAdapter.ViewHolder>
(R.layout.item_school_dymatic_comment) {

    companion object {
        private const val AUTHOR_MAX_LENGTH = 15
        private const val EXTRA_FAIL_COMMENTS = "failComments"
    }

    private var failComments = mutableListOf<SchoolDymaticCommentData>()

    private val mLoadingView: View by lazy {
        LayoutInflater.from(context).inflate(
                R.layout.loading_oa_file, recyclerView.parent as ViewGroup, false
        )
    }

    private val mEmptyView: View by lazy {
        LayoutInflater.from(context).inflate(
                R.layout.empty_oa_file, recyclerView.parent as ViewGroup, false
        )
    }

    private val mErrorView: View by lazy {
        LayoutInflater.from(context).inflate(
                R.layout.error_oa_file, recyclerView.parent as ViewGroup, false
        ).apply {
            setOnClickListener {
                onErrorClickListener?.invoke()
            }
        }
    }

    override fun getStoreData(): Map<String, Any> {
        return (super.getStoreData() as HashMap).apply {
            put(EXTRA_FAIL_COMMENTS, failComments)
        }
    }

    override fun restoreData(storeData: Map<String, Any>) {
        super.restoreData(storeData)
        failComments = (storeData[EXTRA_FAIL_COMMENTS] as? MutableList<SchoolDymaticCommentData>) ?: mutableListOf()
    }

    override fun convert(helper: ViewHolder, item: SchoolDymaticCommentData) {
        helper.bindCommentData(item)
    }

    override fun showError() {
        super.showError()
        emptyView = mErrorView
    }

    override fun showEmpty() {
        super.showEmpty()
        emptyView = mEmptyView
    }

    override fun showPre() {
        super.showPre()
        emptyView = mLoadingView
    }

    override fun setNewData(data: List<SchoolDymaticCommentData>?) {
        super.setNewData(failComments + (data ?: listOf()))
    }

    fun addFailComment(schoolDymaticCommentData: SchoolDymaticCommentData) {
        failComments.add(schoolDymaticCommentData)
        addDataWithState(0, schoolDymaticCommentData, setState = false)
    }

    fun removeFailComment(schoolDymaticCommentData: SchoolDymaticCommentData) {
        failComments.remove(schoolDymaticCommentData)
    }

    fun getRealCount() = data.size - failComments.size

    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {
        fun bindCommentData(commentData: SchoolDymaticCommentData) {
            val comment = commentData.schoolDymaticComment

            // 设置头像
            val options = ImageLoaderOptions().apply {
                errorDrawable = R.drawable.bg_circle_disable
                defaultDrawable = R.drawable.bg_circle_disable
                bitmapTransformation = CircleTransform(context)
            }

            comment.user.image.let {
                if (it == null || it.isBlank()) {
                    mView.headImageView.setImageResource(R.drawable.ic_default_headimg)
                } else {
                    ImageLoader.loadImage(mView.headImageView, it, options)
                }
            }

            val author = comment.user.name.run {
                if (length >= AUTHOR_MAX_LENGTH) {
                    "${substring(0, AUTHOR_MAX_LENGTH)}..."
                } else {
                    this
                }
            }
            if (commentData.isCommentSuccess) {
                val time = TimeFormatUtils.formatTime(comment.postTime, "yyyy-MM-dd HH:mm:ss")
                mView.titleInfoTextView.text = getSpannableTitle(author, time)
            } else {
                val info = "评论发送失败了，已经放入草稿箱，点击重发"
                mView.titleInfoTextView.text = getSpannableTitle(author, info, R.color.colorAccent)
            }



            mView.commentTextView.text = comment.comment
        }

        private fun getSpannableTitle(author: String, time: String,
                                      timeColor: Int = R.color.colorTextDisable): SpannableString {
            val spannable = SpannableString("$author\n$time")
            val authorSpan = arrayOf(AbsoluteSizeSpan(sp2px(13)),
                    StyleSpan(Typeface.BOLD),
                    ForegroundColorSpan(getColorById(R.color.colorTextPrimary)))
            for (span in authorSpan) {
                spannable.setSpan(span, 0, author.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            val timeSpan = arrayOf(AbsoluteSizeSpan(sp2px(12)),
                    ForegroundColorSpan(getColorById(timeColor)))
            for (span in timeSpan) {
                spannable.setSpan(span, author.length, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            return spannable
        }
    }
}