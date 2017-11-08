package com.daijie.ksyllabusapp.data

/**
 * Created by daidaijie on 17-10-16.
 * weeks为[-1]时则代表全周
 */
class CollectionSemesterSyllabus(val weeks: List<Int>) {

    companion object {
        const val FULl_WEEK = -1
        @JvmStatic
        val FULLWEEK_LIST = listOf(FULl_WEEK)
    }

    private val weekSyllabusList = mutableMapOf<Int, CollectionWeekSyllabus>()

    init {
        for (week in weeks) {
            weekSyllabusList.put(week, CollectionWeekSyllabus())
        }
    }

    operator fun get(week: Int): CollectionWeekSyllabus = weekSyllabusList[week]!!
}