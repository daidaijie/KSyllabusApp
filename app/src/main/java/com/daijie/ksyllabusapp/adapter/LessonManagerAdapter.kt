package com.daijie.ksyllabusapp.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by daidaijie on 17-10-10.
 */
class LessonManagerAdapter(fm: FragmentManager, val fragments: Array<Fragment>)
    : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

}