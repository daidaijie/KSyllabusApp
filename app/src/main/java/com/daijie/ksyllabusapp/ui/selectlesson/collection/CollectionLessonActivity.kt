package com.daijie.ksyllabusapp.ui.selectlesson.collection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.ui.selectlesson.collectionlist.CollectionListActivity
import com.daijie.ksyllabusapp.ui.selectlesson.sendsyllabus.SendSyllabusActivity
import kotlinx.android.synthetic.main.activity_collection_lesson.*
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * Created by daidaijie on 2017/10/15.
 */
class CollectionLessonActivity : BaseActivity() {

    companion object {
        @JvmStatic
        fun newIntent(context: Context) = Intent(context, CollectionLessonActivity::class.java)

    }

    override val contentViewId = R.layout.activity_collection_lesson

    override var titleToolbar: Toolbar? = null
        get() = appToolbar

    override var titleText: TextView? = null
        get() = titleTextView

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("课表收集")
        collectionSyllabusView.setOnClickListener {
            startActivity(CollectionListActivity.newIntent(this))
        }
        sendSyllabusView.setOnClickListener {
            startActivity(SendSyllabusActivity.newIntent(this))
        }

    }
}