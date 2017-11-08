package com.daijie.ksyllabusapp.ui.selectlesson.lessonList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.LessonListAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.data.LessonChoice
import com.daijie.ksyllabusapp.data.newGroupByList
import com.daijie.ksyllabusapp.ext.getDrawableById
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.widgets.DividerItemDecoration
import kotlinx.android.synthetic.main.view_select_lesson.*
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * Created by daidaijie on 17-10-9.
 */
class LessonsListActivity : BaseActivity() {

    companion object {
        const val EXTRA_LESSONS = "lessons"

        @JvmStatic
        fun newIntent(context: Context, lessons: Array<Lesson>)
                = Intent(context, LessonsListActivity::class.java).apply {
            putExtra(EXTRA_LESSONS, lessons)
        }
    }

    override var titleToolbar: Toolbar? = null
        get() = appToolbar
    override var titleText: TextView? = null
        get() = titleTextView

    override val contentViewId = R.layout.activity_lesson_list

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("分享课程列表")

        val lessons = intent.getParcelableArrayExtra(EXTRA_LESSONS).map {
            LessonChoice(it as Lesson)
        }.newGroupByList

        selectLessonRecyclerView.layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                getDrawableById(R.drawable.dev_line_padding16)
        )
        itemDecoration.setHeight(1)
        selectLessonRecyclerView.addItemDecoration(itemDecoration)
        val adapter = LessonListAdapter(false)
        selectLessonRecyclerView.adapter = adapter
        adapter.setNewData(lessons)
    }
}