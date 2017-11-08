package com.daijie.ksyllabusapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.ext.getDrawableById
import com.daijie.ksyllabusapp.widgets.baselinearadapter.BaseLinearAdapter
import kotlinx.android.synthetic.main.item_week_day.view.*

/**
 * Created by daidaijie on 17-9-29.
 */
class TimeAdapter(private val gridHeight: Int) : BaseLinearAdapter<TimeAdapter.ViewHolder>() {

    companion object {
        const val times = "1234567890ABC"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_week_day, parent, false))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int, viewType: Int) {
        holder?.bind(position)
    }

    override fun getCount() = times.length

    inner class ViewHolder(val mView: View) : BaseLinearAdapter.ViewHolder(mView) {
        fun bind(position: Int) {
            val lp = mView.weekDayTextView.layoutParams
            lp.height = gridHeight
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT
            mView.weekDayTextView.text = times[position].toString()
            if (position == times.length - 1) {
                mView.weekDayTextView.background = getDrawableById(R.drawable.bg_time_day_end)
            }
        }
    }
}