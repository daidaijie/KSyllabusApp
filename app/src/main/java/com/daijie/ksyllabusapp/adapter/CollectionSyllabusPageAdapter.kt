package com.daijie.ksyllabusapp.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.daijie.ksyllabusapp.data.CollectionSemesterSyllabus
import com.daijie.ksyllabusapp.ui.selectlesson.collectionsyllabus.CollectionSyllabusFragment
import com.daijie.ksyllabusapp.utils.DefaultGson

/**
 * Created by daidaijie on 17-9-28.
 */
class CollectionSyllabusPageAdapter(
        fm: FragmentManager,
        private val collectionSemesterSyllabus: CollectionSemesterSyllabus
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return CollectionSyllabusFragment.newInstance(
                DefaultGson.gson.toJson(collectionSemesterSyllabus[collectionSemesterSyllabus.weeks[position]])
        )
    }

    override fun getCount(): Int = collectionSemesterSyllabus.weeks.size

}