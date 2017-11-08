package com.daijie.ksyllabusapp.ui.syllabus.multilesson

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.MultiLessonAdapter
import com.daijie.ksyllabusapp.adapter.MultiTimeAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.ext.fromJson
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.ui.syllabus.lesson.LessonDetailActivity
import com.daijie.ksyllabusapp.utils.DefaultGson
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.activity_multi_lesson.*

/**
 * Created by daidaijie on 2017/10/17.
 */
class MultiLessonActivity : BaseActivity() {

    companion object {
        const val EXTRA_TIMES = "times"
        const val EXTRA_MULTI_TIMES_LESSONS = "multiTimesLessons"

        @JvmStatic
        fun newIntent(context: Context, times: List<Int>, multiTimesLessons: List<List<Lesson>>)
                = Intent(context, MultiLessonActivity::class.java).apply {
            putExtra(EXTRA_TIMES, DefaultGson.gson.toJson(times))
            val stringList = multiTimesLessons.map {
                DefaultGson.gson.toJson(it)
            }.toTypedArray()
            putExtra(EXTRA_MULTI_TIMES_LESSONS, stringList)
        }
    }

    val times: List<Int> by lazy {
        DefaultGson.gson.fromJson<List<Int>>(intent.getStringExtra(EXTRA_TIMES))
    }

    val multiTimesLessons: List<List<Lesson>> by lazy {
        val stringList = intent.getStringArrayExtra(EXTRA_MULTI_TIMES_LESSONS)
        stringList.map {
            DefaultGson.gson.fromJson<List<Lesson>>(it)
        }.toList()
    }

    override val contentViewId = R.layout.activity_multi_lesson

    override fun initView(savedInstanceState: Bundle?) {
        val params = window.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT

        outerLayout.setOnClickListener {
            finish()
        }

        val timeAdapter = MultiTimeAdapter(times)
        multiTimeDiscreteScrollView.adapter = timeAdapter
        multiTimeDiscreteScrollView.setSlideOnFling(true)
        multiTimeDiscreteScrollView.setItemTransitionTimeMillis(20)
        multiTimeDiscreteScrollView.setSlideOnFlingThreshold(
                20)
        multiTimeDiscreteScrollView.setItemTransformer(ScaleTransformer.Builder()
                .setMaxScale(1.25f).build())
        timeAdapter.setOnItemClickListener { _, _, position ->
            multiTimeDiscreteScrollView.smoothScrollToPosition(position)
            setCurrentTime(position)
        }

        multiTimeDiscreteScrollView.scrollToPosition(0)
        setCurrentTime(0)
    }

    private fun setCurrentTime(timeIndex: Int) {
        val multiLessonAdapter = MultiLessonAdapter(multiTimesLessons[timeIndex])
        multiLessonAdapter.setOnItemChildClickListener { _, _, position ->
            startActivity(LessonDetailActivity.newIntent(
                    this@MultiLessonActivity, multiLessonAdapter.data[position]
            ))
            finish()
        }
        multiLessonDiscreteScrollView.adapter = multiLessonAdapter
        multiLessonDiscreteScrollView.setSlideOnFling(true)
        multiLessonDiscreteScrollView.setItemTransitionTimeMillis(200)
        multiLessonDiscreteScrollView.setItemTransformer(ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build())
    }
}

