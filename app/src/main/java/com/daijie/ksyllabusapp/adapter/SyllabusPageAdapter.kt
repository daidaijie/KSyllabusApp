package com.daijie.ksyllabusapp.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.daijie.ksyllabusapp.ui.syllabus.main.SyllabusFragment

/**
 * Created by daidaijie on 17-9-28.
 */
class SyllabusPageAdapter(fm: FragmentManager, val pageCount: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) = SyllabusFragment.newInstance(position)

    override fun getCount(): Int = pageCount


}