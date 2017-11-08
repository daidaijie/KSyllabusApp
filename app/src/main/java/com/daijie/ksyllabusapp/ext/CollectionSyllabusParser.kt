package com.daijie.ksyllabusapp.ext

import com.daijie.ksyllabusapp.data.CollectionRecord
import com.daijie.ksyllabusapp.data.CollectionSemesterSyllabus
import com.daijie.ksyllabusapp.data.CollectionSemesterSyllabus.Companion.FULLWEEK_LIST
import com.daijie.ksyllabusapp.data.CollectionSemesterSyllabus.Companion.FULl_WEEK
import com.daijie.ksyllabusapp.data.LessonRecord
import com.daijie.ksyllabusapp.utils.DefaultGson

/**
 * Created by daidaijie on 17-10-16.
 */


/**
 * weeks [1,3,4,5] 只解析获取1,3,4,5周的课表
 */
fun parseCollectionEveryWeek(records: List<CollectionRecord>, weeks: List<Int>): CollectionSemesterSyllabus {
    val collectionSemesterSyllabus = CollectionSemesterSyllabus(weeks)
    for (week in weeks) {
        val collectionWeekSyllabus = collectionSemesterSyllabus[week]
        val weekHasSyllabus = 1 shl (week - 1)

        for (record in records) {
            val syllabus = DefaultGson.gson.fromJson<List<List<Int>>>(record.syllabus)
            for ((dateIndex, daySyllabus) in syllabus.withIndex()) {
                for ((timeIndex, weeksHasSyllabus) in daySyllabus.withIndex()) {
                    if ((weekHasSyllabus and weeksHasSyllabus) != 0) {
                        collectionWeekSyllabus.addLessonRecord(
                                LessonRecord(record.account), dateIndex + 1, timeIndex + 1
                        )
                    }
                }
            }
        }
    }
    return collectionSemesterSyllabus
}

fun parseCollectionFullWeek(records: List<CollectionRecord>, weeks: List<Int>): CollectionSemesterSyllabus {
    val collectionSemesterSyllabus = CollectionSemesterSyllabus(FULLWEEK_LIST)
    val collectionWeekSyllabus = collectionSemesterSyllabus[FULl_WEEK]
    var weekHasSyllabus = 0
    for (week in weeks) {
        weekHasSyllabus = weekHasSyllabus or (1 shl (week - 1))
    }
    for (record in records) {
        val syllabus = DefaultGson.gson.fromJson<List<List<Int>>>(record.syllabus)
        for ((dateIndex, daySyllabus) in syllabus.withIndex()) {
            for ((timeIndex, weeksHasSyllabus) in daySyllabus.withIndex()) {
                if ((weekHasSyllabus and weeksHasSyllabus) != 0) {
                    collectionWeekSyllabus.addLessonRecord(
                            LessonRecord(record.account), dateIndex + 1, timeIndex + 1
                    )
                }
            }
        }
    }
    return collectionSemesterSyllabus
}

