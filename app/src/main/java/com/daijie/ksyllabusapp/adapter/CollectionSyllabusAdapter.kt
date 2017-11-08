package com.daijie.ksyllabusapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.data.CollectionWeekSyllabus
import com.daijie.ksyllabusapp.data.LessonRecordGrid
import com.daijie.ksyllabusapp.ui.selectlesson.collectionsyllabus.CollectionAccountsActivity
import com.daijie.ksyllabusapp.widgets.basegridadapter.BaseGridAdapter
import kotlinx.android.synthetic.main.item_lesson_grid.view.*

/**
 * Created by daidaijie on 17-10-16.
 */
class CollectionSyllabusAdapter(val collectionWeekSyllabus: CollectionWeekSyllabus)
    : BaseGridAdapter<CollectionSyllabusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_collection_lesson_grid, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, column: Int, row: Int, viewType: Int) {
        holder.bind(collectionWeekSyllabus[column + 1, row + 1]!!)
    }

    override fun isGridExist(column: Int, row: Int): Boolean {
        return collectionWeekSyllabus[column + 1, row + 1] != null
    }

    override fun getRawSpan(column: Int, row: Int): Int {
        var span = 1
        val date = column + 1
        val time = row + 1
        if (collectionWeekSyllabus[date, time] == null) {
            (time + 1 until rawCount + 1).asSequence()
                    .map { collectionWeekSyllabus[date, it] }
                    .takeWhile { it == null }
                    .forEach { ++span }
        }
        return span
    }

    override fun getColumnCount() = 7

    override fun getRawCount() = 13

    inner class ViewHolder(val mView: View) : BaseGridAdapter.ViewHolder(mView) {
        fun bind(recordGrid: LessonRecordGrid) {
            mView.lessonGridTextView.text = recordGrid.size().toString()
            mView.lessonGridTextView.setOnClickListener {

                val accounts = recordGrid.lessonRecords.map {
                    it.account
                }
                mView.context.startActivity(CollectionAccountsActivity.newIntent(mView.context, accounts))
            }
        }
    }
}