package com.daijie.ksyllabusapp.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.data.LessonTime
import com.daijie.ksyllabusapp.data.formatText
import com.daijie.ksyllabusapp.data.isSelected
import com.daijie.ksyllabusapp.data.lessonWeekString
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.item_lesson_time.view.*

/**
 * Created by daidaijie on 2017/10/2.
 */
class LessonTimeAdapter : BaseQuickAdapter<LessonTime, LessonTimeAdapter.ViewHolder>(R.layout.item_lesson_time) {
    override fun createBaseViewHolder(view: View?): ViewHolder {
        return ViewHolder(view!!)
    }

    override fun convert(helper: ViewHolder, item: LessonTime) {
        helper.bindLessonTime(item, helper.adapterPosition)
    }

    private var onTimeSelectListener: ((LessonTime, Int) -> Unit)? = null
    private var onWeekSelectListener: ((LessonTime, Int) -> Unit)? = null

    fun setOnTimeSelectListener(listener: (lessonTime: LessonTime, adapterPosition: Int) -> Unit) {
        onTimeSelectListener = listener
    }

    fun setOnWeekSelectListener(listener: (lessonTime: LessonTime, adapterPosition: Int) -> Unit) {
        onWeekSelectListener = listener
    }

    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {
        fun bindLessonTime(lessonTime: LessonTime, index: Int) {
            with(lessonTime.timeRange) {
                if (!isSelected) {
                    mView.timeEditLayout.editText.setText("")
                } else {
                    mView.timeEditLayout.editText.setText(formatText)
                }
            }
            mView.timeEditLayout.setOnEditerClickListener {
                onTimeSelectListener?.invoke(lessonTime, index)
            }

            mView.weekEditLayout.setOnEditerClickListener {
                onWeekSelectListener?.invoke(lessonTime, index)
            }
            addOnClickListener(R.id.removeLessonTimeImageView)

            setLessonWeekString(lessonTime)
        }

        fun setLessonWeekString(lessonTime: LessonTime) {
            mView.weekEditLayout.editText.setText(lessonTime.lessonWeekString)
        }
    }
}