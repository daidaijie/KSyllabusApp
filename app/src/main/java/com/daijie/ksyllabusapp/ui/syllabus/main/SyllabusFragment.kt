package com.daijie.ksyllabusapp.ui.syllabus.main

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.SyllabusAdapter
import com.daijie.ksyllabusapp.adapter.TimeAdapter
import com.daijie.ksyllabusapp.adapter.WeekDayAdapter
import com.daijie.ksyllabusapp.base.BaseFragment
import com.daijie.ksyllabusapp.data.SemesterSyllabus
import com.daijie.ksyllabusapp.data.WeekSyllabus
import com.daijie.ksyllabusapp.ext.initColor
import kotlinx.android.synthetic.main.fragment_syllabus.*
import kotlin.properties.Delegates

/**
 * Created by daidaijie on 17-9-28.
 */
class SyllabusFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, SyllabusReceiver {

    companion object {
        const val EXTRA_POSITION = "TimePoint"

        @JvmStatic
        fun newInstance(position: Int): SyllabusFragment = SyllabusFragment().apply {
            val args = Bundle()
            args.putInt(EXTRA_POSITION, position)
            arguments = args
        }
    }

    override val contentViewId = R.layout.fragment_syllabus

    private var position: Int by Delegates.notNull()

    private var timeGridWidth: Int by Delegates.notNull()
    private var lessonGridWidth: Int by Delegates.notNull()
    private var gridHeight: Int by Delegates.notNull()
    private val syllabusAdapter = SyllabusAdapter()


    override fun initView(savedInstanceState: Bundle?) {
        position = arguments.getInt(EXTRA_POSITION, -1)
        initSize()
        initRefresh()
        initWeekDay()
        initTimes()
        initSyllabus()
        SyllabusSender.register(this)
    }

    override fun onDestroyView() {
        SyllabusSender.unregister(this)
        super.onDestroyView()
    }

    private fun initSyllabus() {
        (act as? SyllabusActivity)?.cacheSyllabus?.get(position)?.let {
            showSyllabus(it)
        }
    }

    override fun onSyllabusReceived(syllabus: SemesterSyllabus?) {
        if (syllabus != null) {
            syllabus[position]?.let {
                showSyllabus(it)
            }
        }
        hideRefresh()
    }

    private fun showSyllabus(syllabus: WeekSyllabus) {
        syllabusAdapter.syllabus = syllabus
        syllabusAdapter.attachTo(syllabusGridLayout)
    }

    private fun initTimes() {
        timeLinearLayout.layoutParams.width = timeGridWidth
        val timeAdapter = TimeAdapter(gridHeight)
        timeAdapter.attachTo(timeLinearLayout)
    }

    private fun initWeekDay() {
        val weekDayAdapter = WeekDayAdapter(timeGridWidth, lessonGridWidth)
        weekDayAdapter.attachTo(dateLinearLayout)
    }

    private fun initRefresh() {
        swipeRefreshLayout.initColor()
        swipeRefreshLayout.setScrollView(swipeRefreshScrollView)
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun initSize() {
        lessonGridWidth = act.actWidth * 2 / 15
        timeGridWidth = act.actWidth - lessonGridWidth * 7
        val defaultHeight = resources.getDimensionPixelOffset(R.dimen.syllabus_grid_height)
        val minHeight = (act.actHeight
                - act.mStatusBarHeight
                - resources.getDimensionPixelOffset(R.dimen.toolbarHeight)
                - resources.getDimensionPixelOffset(R.dimen.week_grid_height)) / 13

        if (defaultHeight >= minHeight) {
            gridHeight = defaultHeight
        } else {
            gridHeight = minHeight
            val weekHeight = (act.actHeight
                    - act.mStatusBarHeight
                    - resources.getDimensionPixelOffset(R.dimen.toolbarHeight)
                    - minHeight * 13)
            dateLinearLayout.layoutParams.height = weekHeight
        }
    }

    override fun onRefresh() {
        (act as? SyllabusActivity)?.onRefreshCallback?.invoke()
    }

    private fun showRefresh() {
        swipeRefreshLayout.isRefreshing = true
    }

    private fun hideRefresh() {
        swipeRefreshLayout.isRefreshing = false
    }


}