package com.daijie.ksyllabusapp.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.ext.clipboard
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.repository.lesson.ShareLessonBaseInfo
import com.daijie.ksyllabusapp.ui.selectlesson.lessonList.LessonsListActivity
import kotlinx.android.synthetic.main.item_share_list.view.*

/**
 * Created by daidaijie on 2017/10/7.
 */
class ShareListAdapter() : BaseQuickAdapter<ShareLessonBaseInfo, ShareListAdapter.ViewHolder>(R.layout.item_share_list) {

    override fun convert(helper: ViewHolder, item: ShareLessonBaseInfo) {
        helper.bindRecord(item)
    }


    inner class ViewHolder(private val mView: View) : BaseViewHolder(mView) {
        fun bindRecord(shareLessonBaseInfo: ShareLessonBaseInfo) {
            mView.objectIdTextView.text = "分享码: ${shareLessonBaseInfo.objectId}"
            mView.lessonNumTextView.text = "课程数: ${shareLessonBaseInfo.lessons.size}门"
            mView.copyButton.setOnClickListener {
                clipboard = shareLessonBaseInfo.objectId
                App.context.toastS("复制成功")
            }
            mView.shareItemLayout.setOnClickListener {
                mView.context.startActivity(LessonsListActivity.newIntent(mView.context,
                        shareLessonBaseInfo.lessons.toTypedArray()))
            }
            addOnClickListener(R.id.deleteButton)
        }
    }
}