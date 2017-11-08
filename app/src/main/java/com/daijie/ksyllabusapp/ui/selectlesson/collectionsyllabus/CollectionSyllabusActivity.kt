package com.daijie.ksyllabusapp.ui.selectlesson.collectionsyllabus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.CollectionSyllabusPageAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.data.CollectionSemesterSyllabus
import com.daijie.ksyllabusapp.data.CollectionSemesterSyllabus.Companion.FULl_WEEK
import com.daijie.ksyllabusapp.ext.fromJson
import com.daijie.ksyllabusapp.utils.DefaultGson
import kotlinx.android.synthetic.main.activity_collection_syllabus.*
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * Created by daidaijie on 17-10-16.
 */
class CollectionSyllabusActivity : BaseActivity() {

    companion object {
        const val EXTRA_SYLLABUS_JSON = "syllabsuJson"

        fun newIntent(context: Context, syllabusJson: String)
                = Intent(context, CollectionSyllabusActivity::class.java).apply {
            putExtra(EXTRA_SYLLABUS_JSON, syllabusJson)
        }
    }

    override var titleToolbar: Toolbar? = null
        get() = appToolbar

    override var titleText: TextView? = null
        get() = titleTextView

    val syllabus: CollectionSemesterSyllabus by lazy {
        DefaultGson.gson.fromJson<CollectionSemesterSyllabus>(intent.getStringExtra(EXTRA_SYLLABUS_JSON))
    }

    override val contentViewId = R.layout.activity_collection_syllabus

    override fun initView(savedInstanceState: Bundle?) {
        weekSyllabusViewPager.adapter = CollectionSyllabusPageAdapter(
                supportFragmentManager, syllabus
        )
        weekSyllabusViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                showTitle(position)
            }

        })
        showTitle()
    }

    private fun showTitle(position: Int = weekSyllabusViewPager.currentItem) {
        if (syllabus.weeks[position] == FULl_WEEK) {
            setTitle("全周")
        } else {
            setTitle("第${syllabus.weeks[position]}周")
        }

    }
}