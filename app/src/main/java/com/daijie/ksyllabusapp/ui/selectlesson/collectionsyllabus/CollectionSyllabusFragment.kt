package com.daijie.ksyllabusapp.ui.selectlesson.collectionsyllabus

import android.os.Bundle
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.CollectionSyllabusAdapter
import com.daijie.ksyllabusapp.adapter.TimeAdapter
import com.daijie.ksyllabusapp.adapter.WeekDayAdapter
import com.daijie.ksyllabusapp.base.BaseFragment
import com.daijie.ksyllabusapp.data.CollectionWeekSyllabus
import com.daijie.ksyllabusapp.ext.fromJson
import com.daijie.ksyllabusapp.utils.DefaultGson
import kotlinx.android.synthetic.main.fragment_syllabus.*
import kotlin.properties.Delegates

/**
 * Created by daidaijie on 17-10-16.
 */
class CollectionSyllabusFragment : BaseFragment() {

    companion object {
        const val EXTRA_WEEK_SYLLABUS_JSON = "weekSyllabusJson"

        fun newInstance(weekSyllabusJson: String) = CollectionSyllabusFragment().apply {
            val args = Bundle()
            args.putString(EXTRA_WEEK_SYLLABUS_JSON, weekSyllabusJson)
            arguments = args
        }
    }

    private var timeGridWidth: Int by Delegates.notNull()
    private var lessonGridWidth: Int by Delegates.notNull()
    private var gridHeight: Int by Delegates.notNull()

    private val weekSyllabus: CollectionWeekSyllabus by lazy {
        DefaultGson.gson.fromJson<CollectionWeekSyllabus>(arguments.getString(EXTRA_WEEK_SYLLABUS_JSON))
    }

    override val contentViewId = R.layout.fragment_syllabus

    override fun initView(savedInstanceState: Bundle?) {
        swipeRefreshLayout.isEnabled = false
        initSize()
        initWeekDay()
        initTimes()
        initSyllabus()
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

    private fun initWeekDay() {
        val weekDayAdapter = WeekDayAdapter(timeGridWidth, lessonGridWidth)
        weekDayAdapter.attachTo(dateLinearLayout)
    }

    private fun initTimes() {
        timeLinearLayout.layoutParams.width = timeGridWidth
        val timeAdapter = TimeAdapter(gridHeight)
        timeAdapter.attachTo(timeLinearLayout)
    }

    private fun initSyllabus() {
        val adapter = CollectionSyllabusAdapter(weekSyllabus)
        adapter.attachTo(syllabusGridLayout)
    }
}