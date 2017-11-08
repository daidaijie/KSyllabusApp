package com.daijie.ksyllabusapp.ui.addlesson

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.view.View
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.base.BaseDialogFragment
import com.daijie.ksyllabusapp.data.TimeRange
import com.daijie.ksyllabusapp.data.isSelected
import com.daijie.ksyllabusapp.ext.weeks
import kotlinx.android.synthetic.main.dialog_select_time.view.*
import kotlin.properties.Delegates

/**
 * Created by daidaijie on 2017/10/3.
 */
class SelectTimeDialog : BaseDialogFragment() {

    override val contentViewId = R.layout.dialog_select_time

    private var timeRange: TimeRange by Delegates.notNull()

    private var onTimeSelected: ((Int, Int, Int) -> Unit)? = null

    companion object {
        const val DIALOG_SELECT_TIME = "SelectTimeDialog"
        const val EXTRA_TIME_RANGE = "timeRange"

        val startTimes = List(13, { index ->
            "第${index + 1}节"
        })
        val endTimes = List(13, { index ->
            "到${index + 1}节"
        })

        @JvmStatic
        fun newInstance(timeRange: TimeRange) = SelectTimeDialog().apply {
            val args = Bundle()
            args.putParcelable(EXTRA_TIME_RANGE, timeRange)
            arguments = args
        }
    }

    fun show(fg: FragmentManager) {
        this.show(fg, DIALOG_SELECT_TIME)
    }

    fun setOnTimeSelectedListener(listener: (date: Int, startTime: Int, endTime: Int) -> Unit) {
        onTimeSelected = listener
    }

    override fun initDialog(view: View, savedInstanceState: Bundle?): Dialog {
        timeRange = arguments.getParcelable(EXTRA_TIME_RANGE)
        var weeksSelected = 0
        var startTimeSelected = 0
        var endTimeSelected = 0
        if (timeRange.isSelected) {
            weeksSelected = timeRange.date - 1
            startTimeSelected = timeRange.startTime - 1
            endTimeSelected = timeRange.endTime - 1
        }

        view.dateScrollChoice.addItems(weeks, weeksSelected)
        view.startTimeScrollChoice.addItems(startTimes, startTimeSelected)
        view.endTimeScrollChoice.addItems(endTimes, endTimeSelected)

        view.startTimeScrollChoice.setOnItemSelectedListener { _, position, _ ->
            if (position > view.endTimeScrollChoice.currentItemPosition) {
                view.endTimeScrollChoice.scrollTo(position)
            }
        }
        view.endTimeScrollChoice.setOnItemSelectedListener { _, position, _ ->
            if (position <= view.startTimeScrollChoice.currentItemPosition) {
                view.endTimeScrollChoice.scrollTo(view.startTimeScrollChoice.currentItemPosition)
            }
        }

        return AlertDialog.Builder(activity)
                .setTitle("选择上课节数")
                .setView(view)
                .setPositiveButton("确定", { _, _ ->
                    val date = view.dateScrollChoice.currentItemPosition + 1
                    val startTime = view.startTimeScrollChoice.currentItemPosition + 1
                    var endTime = view.endTimeScrollChoice.currentItemPosition + 1
                    endTime = if (endTime >= startTime) endTime else startTime
                    onTimeSelected?.invoke(
                            date, startTime, endTime
                    )
                })
                .setNegativeButton("取消", null)
                .create()
    }


}