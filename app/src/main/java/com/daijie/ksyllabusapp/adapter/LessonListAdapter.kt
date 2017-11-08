package com.daijie.ksyllabusapp.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.data.LessonChoice
import com.daijie.ksyllabusapp.ui.syllabus.lesson.LessonDetailActivity
import kotlinx.android.synthetic.main.item_lesson_list.view.*

/**
 * Created by daidaijie on 2017/10/6.
 */
class LessonListAdapter(val selectable: Boolean = true)
    : BaseQuickAdapter<LessonChoice, LessonListAdapter.ViewHolder>(R.layout.item_lesson_list) {

    override fun convert(helper: ViewHolder, item: LessonChoice) {
        helper.bindLessonChoice(item)
    }


    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {

        fun bindLessonChoice(lessonChoice: LessonChoice) {
            if (lessonChoice.isHeader) {
                mView.lessonTypeTextView.text = if (lessonChoice.lesson.fromCreditSystem) "学分制课程" else "自定义课程"
                mView.lessonSelectedHead.visibility = View.VISIBLE

            } else {
                mView.lessonSelectedHead.visibility = View.GONE
            }
            mView.lessonNameTextView.text = lessonChoice.lesson.simpleName
            mView.lessonSelectedLayout.setOnClickListener {
                mView.context.startActivity(LessonDetailActivity.newIntent(mView.context,
                        lessonChoice.lesson))
            }

            mView.lessonSelectedCheckBox.isEnabled = lessonChoice.isSelectable

            if (selectable && lessonChoice.isSelectable) {
                mView.lessonSelectedCheckBox.visibility = View.VISIBLE
                mView.lessonSelectedCheckBox.isChecked = lessonChoice.isSelected
                with(mView.lessonSelectedCheckBox) {
                    this.setOnClickListener {
                        lessonChoice.isSelected = isChecked
                    }
                    mView.checkboxLayout.setOnClickListener {
                        isChecked = !isChecked
                        lessonChoice.isSelected = isChecked
                    }
                }
            } else if (!selectable) {
                mView.lessonSelectedCheckBox.visibility = View.INVISIBLE
            }
        }
    }

}