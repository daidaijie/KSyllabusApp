package com.daijie.ksyllabusapp.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.ui.syllabus.lesson.LessonDetailActivity
import kotlinx.android.synthetic.main.item_lesson_detail.view.*

/**
 * Created by daidaijie on 17-10-10.
 */
class LessonDetailListAdapter
    : BaseQuickAdapter<Lesson, LessonDetailListAdapter.ViewHolder>(R.layout.item_lesson_detail) {

    override fun convert(helper: ViewHolder, item: Lesson) {
        helper.bindLesson(item)
    }

    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {
        fun bindLesson(lesson: Lesson) {
            mView.lessonNameTextLayout.setInfo(lesson.simpleName)
            mView.lessonTeacherTextLayout.setInfo(lesson.teacher)
            mView.lessonAddressTextLayout.setInfo(lesson.room)
            mView.lessonTimeTextLayout.setInfo(lesson.lessonTime)
            mView.lessonItemLayout.setOnClickListener {
                mView.context.startActivity(LessonDetailActivity.newIntent(mView.context, lesson))
            }
        }
    }

}