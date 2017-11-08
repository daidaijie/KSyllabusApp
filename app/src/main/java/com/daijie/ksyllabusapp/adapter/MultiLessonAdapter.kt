package com.daijie.ksyllabusapp.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import kotlinx.android.synthetic.main.item_multi_lesson.view.*

/**
 * Created by daidaijie on 17-10-18.
 */
class MultiLessonAdapter(lessons: List<Lesson>)
    : BaseQuickAdapter<Lesson, MultiLessonAdapter.ViewHolder>(R.layout.item_multi_lesson, lessons) {

    override fun convert(helper: ViewHolder, item: Lesson) {
        helper.bindLeson(item)
    }


    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {
        fun bindLeson(lesson: Lesson) {
            mView.lessonInfoTextView.text = lesson.gridText
        }
    }
}