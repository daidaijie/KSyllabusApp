package com.daijie.ksyllabusapp.ui.syllabus.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.SyllabusPageAdapter
import com.daijie.ksyllabusapp.ui.addlesson.AddLessonActivity
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.data.SemesterSyllabus
import com.daijie.ksyllabusapp.ext.toastE
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.ui.selectlesson.addshare.AddShareActivity
import com.daijie.ksyllabusapp.ui.selectlesson.collection.CollectionLessonActivity
import com.daijie.ksyllabusapp.ui.selectlesson.sharelist.ShareListActivity
import com.daijie.ksyllabusapp.ui.syllabus.manager.LessonsManagerActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_syllabus.*
import kotlinx.android.synthetic.main.content_syllabus.*
import kotlinx.android.synthetic.main.nav_header_syllabus.view.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by daidaijie on 17-9-28.
 */
class SyllabusActivity : BaseActivity(), SyllabusContract.View,
        NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var syllabusPresenter: SyllabusPresenter

    var headerView: View by Delegates.notNull()

    var isCreated = false

    val addLessonDialog: AlertDialog by lazy {
        AlertDialog.Builder(this)
                .setItems(arrayOf("手动添加", "使用分享码添加"), { _, i ->
                    when (i) {
                        0 -> startActivityForResult(AddLessonActivity.newIntent(this), REQUEST_ADD_LESSON)
                        1 -> startActivityForResult(AddShareActivity.newIntent(this), REQUEST_ADD_LESSON)
                    }
                }).create()
    }

    val onRefreshCallback: (() -> Unit) = {
        syllabusPresenter.getSyllabus()
    }

    val cacheSyllabus: SemesterSyllabus?
        get() = if (isCreated) syllabusPresenter.cacheSyllabus else null

    override var titleToolbar: Toolbar? = null
        get() = appToolbar
    override var titleText: TextView? = null
        get() = titleTextView

    companion object {
        const val REQUEST_ADD_LESSON = 200

        @JvmStatic
        fun newIntent(context: Context) = Intent(context, SyllabusActivity::class.java)
    }

    override val contentViewId: Int = R.layout.activity_syllabus

    private var syllabusPageAdapter: SyllabusPageAdapter by Delegates.notNull()

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("汕大课程表")
        headerView = nav_view.getHeaderView(0)
        initInject()
        initDrawer()
        initPager()
        syllabusPresenter.subscribe()
        isCreated = true
    }

    override fun onDestroy() {
        isCreated = false
        syllabusPresenter.dispose()
        super.onDestroy()
    }

    private fun initInject() {
        DaggerSyllabusComponent.builder()
                .userComponent(App.userComponent)
                .syllabusModule(SyllabusModule(this))
                .build().inject(this)
    }

    private fun initPager() {
        syllabusPageAdapter = SyllabusPageAdapter(supportFragmentManager, 16)
        weekSyllabusViewPager.adapter = syllabusPageAdapter
    }

    private fun initDrawer() {
        val toggle = ActionBarDrawerToggle(this, drawer_layout, appToolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_add_lesson -> addLessonDialog.show()
            R.id.nav_share_lesson -> startActivity(ShareListActivity.newIntent(this))
            R.id.nav_lesson_manager -> startActivity(LessonsManagerActivity.newIntent(this))
            R.id.nav_lesson_collection -> startActivity(CollectionLessonActivity.newIntent(this))
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Logger.d("onActivityResult $requestCode $resultCode")
        if (requestCode == REQUEST_ADD_LESSON && resultCode == Activity.RESULT_OK) {
            toastS("刷新")
            syllabusPresenter.getSyllabus(false)
        }
    }

    override fun setNickname(nickname: String) {
        headerView.nicknameTextView.text = nickname
    }

    override fun setSemesterText(semesterText: String) {
        headerView.semesterTextView.text = semesterText
    }

    override fun showSuccess(msg: String) {
        toastS(msg)
    }

    override fun showError(msg: String) {
        toastE(msg)
    }
}