package com.daijie.ksyllabusapp.repository.semester.local

import android.content.Context
import android.content.SharedPreferences
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.ext.month
import com.daijie.ksyllabusapp.ext.read
import com.daijie.ksyllabusapp.ext.write
import com.daijie.ksyllabusapp.ext.year
import com.daijie.ksyllabusapp.repository.semester.Semester
import com.daijie.ksyllabusapp.repository.semester.SemesterDataSource
import com.daijie.ksyllabusapp.utils.TimeUtils
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by daidaijie on 17-9-29.
 */
class SemesterLocalDataSource : SemesterDataSource {

    companion object {
        const val EXTRA_CURRENT_SEMESTER = "CurrentSemester"
    }

    override var currentSemester: Semester by SemesterDelegate()

    private class SemesterDelegate
        : ReadWriteProperty<Any?, Semester> {

        private val semesterPreferences: SharedPreferences by lazy {
            App.context.getSharedPreferences("Semester", Context.MODE_PRIVATE)
        }

        private val defaultSemester: Semester by lazy {
            val today = TimeUtils.today
            val queryYear: Int
            val querySem: Int

            val year = today.year
            val month = today.month
            when (month) {
                in 2..7 -> {
                    queryYear = year - 1
                    querySem = 2
                }
                8 -> {
                    queryYear = year
                    querySem = 3
                }
                else -> {
                    queryYear = if (month > 8) {
                        year
                    } else {
                        year - 1
                    }
                    querySem = 1
                }
            }

            return@lazy Semester(queryYear, querySem)
        }

        private var value: Semester? = null

        override fun getValue(thisRef: Any?, property: KProperty<*>): Semester {
            return value ?: semesterPreferences.read(EXTRA_CURRENT_SEMESTER, defaultSemester, writeDefault = true)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Semester) {
            this.value = value
            semesterPreferences.write(EXTRA_CURRENT_SEMESTER, value)
        }
    }
}