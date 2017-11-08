package com.daijie.ksyllabusapp.ui.dymatic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.SchoolDymaticPagerAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.ui.dymatic.circle.list.SchoolCircleFragment
import com.daijie.ksyllabusapp.ui.dymatic.circle.send.SchoolCircleSendActivity
import com.daijie.ksyllabusapp.ui.dymatic.notice.list.SchoolNoticeFragment
import kotlinx.android.synthetic.main.activity_school_dymatic.*

/**
 * Created by daidaijie on 2017/10/22.
 */
class SchoolDymaticActivity : BaseActivity() {

    companion object {
        @JvmStatic
        fun newIntent(context: Context) = Intent(context, SchoolDymaticActivity::class.java)
    }

    override val contentViewId = R.layout.activity_school_dymatic

    override var titleToolbar: Toolbar? = null
        get() = appToolbar

    private val schoolCircleFragment = SchoolCircleFragment.newInstance()
    private val schoolNoticeFragment = SchoolNoticeFragment.newInstance()

    private val schoolDymaticPagerAdapter: SchoolDymaticPagerAdapter by lazy {
        SchoolDymaticPagerAdapter(supportFragmentManager,
                arrayOf(schoolNoticeFragment, schoolCircleFragment))
    }

    override fun initView(savedInstanceState: Bundle?) {
        schoolDymaticViewPager.adapter = schoolDymaticPagerAdapter
        tabLayout.setupWithViewPager(schoolDymaticViewPager)
        tabLayout.getTabAt(0)?.text = "校园动态"
        tabLayout.getTabAt(1)?.text = "消息圈"

        fab.setOnClickListener {
            when (schoolDymaticViewPager.currentItem) {
                1 -> {
                    startActivity(SchoolCircleSendActivity.newIntent(this))
                }
            }
        }
    }


}