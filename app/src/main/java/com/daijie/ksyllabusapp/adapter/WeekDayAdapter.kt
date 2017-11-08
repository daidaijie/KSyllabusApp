package com.daijie.ksyllabusapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.R.id.weekDayTextView
import com.daijie.ksyllabusapp.ext.getDrawableById
import com.daijie.ksyllabusapp.widgets.baselinearadapter.BaseLinearAdapter
import kotlinx.android.synthetic.main.item_week_day.*
import kotlinx.android.synthetic.main.item_week_day.view.*

/**
 * Created by daidaijie on 17-9-29.
 */
class WeekDayAdapter(
        private val blankGridWidth: Int,
        private val weekDayGridWith: Int
) : BaseLinearAdapter<WeekDayAdapter.ViewHolder>() {

    companion object {
        @JvmStatic
        val weeks = arrayOf("", "周一", "周二", "周三", "周四", "周五", "周六", "周日")
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_week_day, parent, false))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int, viewType: Int) {
        holder?.bind(position)
    }

    override fun getCount() = weeks.size

    inner class ViewHolder(val mView: View) : BaseLinearAdapter.ViewHolder(mView) {
        fun bind(position: Int) {
            val date = weeks[position]
            val lp = mView.weekDayTextView.layoutParams
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT
            if (date.isEmpty()) {
                lp.width = blankGridWidth
            } else {
                mView.weekDayTextView.text = date
                if (position == 7) {
                    mView.weekDayTextView.background = getDrawableById(R.drawable.bg_week_day_end)
                }
                lp.width = weekDayGridWith
            }
        }
    }
}