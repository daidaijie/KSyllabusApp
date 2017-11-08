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
import com.daijie.ksyllabusapp.data.SchoolNotice
import com.daijie.ksyllabusapp.ext.getColorById
import com.daijie.ksyllabusapp.ext.sp2px
import com.daijie.ksyllabusapp.utils.TimeFormatUtils
import com.daijie.ksyllabusapp.widgets.CustomLoadMoreView
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.item_school_dymatic_content.view.*
import kotlin.properties.Delegates

/**
 * Created by daidaijie on 2017/10/24.
 */
abstract class SchoolDymaticAdapter<V : SchoolDymaticAdapter.SchoolDymaticViewHolder>(
        protected val context: Context
) : BaseQuickStateAdapter<SchoolDymaticData, V>(R.layout.item_school_notice) {

    companion object {
        const val PAGE_LIMIT = 10
        private const val AUTHOR_MAX_LENGTH = 15
    }

    init {
        setLoadMoreView(CustomLoadMoreView())
    }

    private var mLikeStatusChangeListener: ((schoolDymaticData: SchoolDymaticData,
                                             isLike: Boolean,
                                             likeButton: LikeButton,
                                             likeTextView: TextView) -> Unit)? = null

    fun setLikeStatusChangeListener(listener: (schoolDymaticData: SchoolDymaticData,
                                               isLike: Boolean,
                                               likeButton: LikeButton,
                                               likeTextView: TextView) -> Unit) {
        mLikeStatusChangeListener = listener
    }


    private val mErrorView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.error_view, recyclerView as ViewGroup, false)
                .apply {
                    val errorTextView = findViewById<TextView>(R.id.errTextView);
                    errorTextView.text = "获取失败\n请点击重试"
                    setOnClickListener {
                        onErrorClickListener?.invoke()
                    }
                }
    }

    override fun showError() {
        super.showError()
        emptyView = mErrorView
    }


    override fun convert(helper: V, item: SchoolDymaticData) {
        helper.bindDymaticSchoolData(item, mLikeStatusChangeListener)
    }

    abstract fun getDymaticContent(schoolDymaticData: SchoolDymaticData): String

    abstract class SchoolDymaticViewHolder(
            protected val mView: View)
        : BaseViewHolder(mView) {

        protected var schoolDymaticData: SchoolDymaticData by Delegates.notNull()

        protected var schoolNotice: SchoolNotice by Delegates.notNull()

        fun bindDymaticSchoolData(schoolDymaticData: SchoolDymaticData,
                                  likeStatusChangeListener: ((schoolDymaticData: SchoolDymaticData,
                                                              isLike: Boolean,
                                                              likeButton: LikeButton,
                                                              likeTextView: TextView) -> Unit)?) {
            this.schoolDymaticData = schoolDymaticData
            schoolNotice = schoolDymaticData.schoolNotice

            // 设置头部
            val author = author()
            val showAuthor = author.run {
                if (length >= AUTHOR_MAX_LENGTH) {
                    "${substring(0, AUTHOR_MAX_LENGTH)}..."
                } else {
                    this
                }
            }
            val timeAndSource = "${TimeFormatUtils.formatTime(schoolNotice.postTime, "yyyy-MM-dd HH:mm:ss")}　${source()}"
            mView.titleInfoTextView.text = getSpannableTitle(showAuthor, timeAndSource)

            //设置正文
            mView.contentTextView.text = content()

            // 设置图片
            val dymaticPhotos = schoolNotice.photos
            if (dymaticPhotos.isNotEmpty()) {
                val photos = dymaticPhotos.map {
                    it.smallImg
                }
                val gridAdapter = SchoolDymaticGridImageViewAdapter()
                mView.gridImageView.setAdapter(gridAdapter)
                mView.gridImageView.setImagesData(photos)
                mView.gridImageView.visibility = View.VISIBLE
            } else {
                mView.gridImageView.visibility = View.GONE
            }

            // 设置点赞Button
            mView.likeButton.isLiked = schoolDymaticData.isMyLike
            mView.likeLayout.setOnClickListener {
                mView.likeButton.onClick(mView.likeButton)
            }
            mView.likeNumTextView.text = "${schoolNotice.schoolDymaticThumbUps?.size ?: 0}"
            mView.likeButton.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton) {
                    likeStatusChangeListener?.invoke(schoolDymaticData, true, likeButton, mView.likeNumTextView)
                }

                override fun unLiked(likeButton: LikeButton) {
                    likeStatusChangeListener?.invoke(schoolDymaticData, false, likeButton, mView.likeNumTextView)
                }

            })

            // 设置评论Button
            mView.commentNumTextView.text = "${schoolNotice.comments?.size ?: 0}"

            afterBaseBindSchoolDymaticData(schoolDymaticData)

            addOnClickListener(R.id.commentLayout)
        }


        abstract fun afterBaseBindSchoolDymaticData(schoolDymaticData: SchoolDymaticData)

        abstract fun author(): String
        abstract fun source(): String
        abstract fun content(): String

        private fun getSpannableTitle(author: String, time: String): SpannableString {
            val spannable = SpannableString("$author\n$time")
            val authorSpan = arrayOf(AbsoluteSizeSpan(sp2px(15)),
                    StyleSpan(Typeface.BOLD),
                    ForegroundColorSpan(getColorById(R.color.colorTextPrimary)))
            for (span in authorSpan) {
                spannable.setSpan(span, 0, author.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            val timeSpan = arrayOf(AbsoluteSizeSpan(sp2px(12)),
                    ForegroundColorSpan(getColorById(R.color.colorTextDisable)))
            for (span in timeSpan) {
                spannable.setSpan(span, author.length, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            return spannable
        }
    }

}