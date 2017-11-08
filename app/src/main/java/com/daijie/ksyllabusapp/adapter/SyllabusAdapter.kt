package com.daijie.ksyllabusapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.data.WeekSyllabus
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.ui.syllabus.lesson.LessonDetailActivity
import com.daijie.ksyllabusapp.ui.syllabus.multilesson.MultiLessonActivity
import com.daijie.ksyllabusapp.widgets.basegridadapter.BaseGridAdapter
import kotlinx.android.synthetic.main.item_lesson_grid.view.*

/**
 * Created by daidaijie on 17-9-30.
 */
class SyllabusAdapter() : BaseGridAdapter<SyllabusAdapter.ViewHolder>() {

    var syllabus: WeekSyllabus? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_lesson_grid, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, column: Int, row: Int, viewType: Int) {
        holder.bind(syllabus!![column + 1, row + 1]!![0], column + 1, row + 1)
    }

    override fun isGridExist(column: Int, row: Int): Boolean {
        if (syllabus == null) {
            return false
        } else {
            return syllabus!![column + 1, row + 1] != null
        }
    }

    override fun getRawSpan(column: Int, row: Int): Int {
        var span = 1
        val date = column + 1
        val time = row + 1
        val lessons = syllabus!![date, time]
        if (lessons != null) {
            (time + 1 until rawCount + 1).asSequence()
                    .map { syllabus!![date, it] }
                    .takeWhile { lessons.isSame(it) }
                    .forEach { ++span }
        } else {
            (time + 1 until rawCount + 1).asSequence()
                    .map { syllabus!![date, it] }
                    .takeWhile { it == null }
                    .forEach { ++span }
        }
        return span
    }

    override fun getColumnCount() = 7

    override fun getRawCount() = 13

    inner class ViewHolder(val mView: View) : BaseGridAdapter.ViewHolder(mView) {
        fun bind(lesson: Lesson, date: Int, time: Int) {
            mView.lessonGridTextView.text = lesson.name
            val lessons = syllabus!![date, time]!!.lessons
            mView.lessonGridTextView.setOnClickListener {
                mView.context.toastS("rowCount = $rowCount")
                if (lessons.size == 1) {
                    mView.context.startActivity(
                            LessonDetailActivity.newIntent(mView.context, lesson)
                    )
                } else {
                    val times = List(rowCount, {
                        time + it
                    })
                    val multiTimesLessons = List(rowCount, {
                        syllabus!![date, times[it]]!!.lessons
                    })
                    mView.context.startActivity(
                            MultiLessonActivity.newIntent(
                                    mView.context, times, multiTimesLessons
                            )
                    )
                }
            }
        }
    }
}