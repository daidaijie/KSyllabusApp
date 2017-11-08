package com.daijie.ksyllabusapp.ui.syllabus.manager

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.LessonDetailListAdapter
import com.daijie.ksyllabusapp.base.BaseFragment
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.empty_normal.view.*
import kotlinx.android.synthetic.main.fragment_lessons_detail_list.*
import kotlin.properties.Delegates

/**
 * Created by daidaijie on 17-10-10.
 */
class LessonsDetailListFragment : BaseFragment() {

    var lessons: List<Lesson>? = null

    var hasLoad: Boolean = false

    companion object {
        @JvmStatic
        fun newInstance() = LessonsDetailListFragment()
    }

    var emptyView: View by Delegates.notNull()

    override val contentViewId = R.layout.fragment_lessons_detail_list

    private val lessonDetailListAdapter = LessonDetailListAdapter()

    override fun initView(savedInstanceState: Bundle?) {
        Logger.d("onCreateView")
        lessonDetailListRecyclerView.layoutManager = LinearLayoutManager(act)
        lessonDetailListRecyclerView.adapter = lessonDetailListAdapter
        emptyView = LayoutInflater.from(act)
                .inflate(
                        R.layout.empty_normal, lessonDetailListRecyclerView.parent as ViewGroup, false
                ) as ViewGroup
        emptyView.emptyTextView.text = "暂无该类型课程"

        if (hasLoad) {
            if (lessons != null) {
                lessonDetailListAdapter.setNewData(lessons)
            } else {
                lessonDetailListAdapter.emptyView = emptyView
            }
        }
    }

    fun showData(lessons: List<Lesson>?) {
        this.lessons = lessons
        hasLoad = true
        if (lessons != null) {
            lessonDetailListAdapter.setNewData(lessons)
        } else {
            lessonDetailListAdapter.emptyView = emptyView
        }
    }


}