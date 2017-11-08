package com.daijie.ksyllabusapp.data

import com.daijie.ksyllabusapp.ext.char2time
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.repository.lessonrule.LessonRule

/**
 * Created by daidaijie on 17-9-29.
 */
class WeekSyllabus {

    private val lessonMapGrids: MutableMap<TimePoint, LessonGrid> = mutableMapOf()

    fun addLesson(lesson: Lesson, date: Int, times: String, lessonRule: LessonRule) {
        var fixDate = date
        if (fixDate == 0) {
            fixDate = 7
        }
        for (timeChar in times) {
            val time = char2time(timeChar)
            val timePoint = TimePoint(fixDate, time)
            if (lessonMapGrids[timePoint] == null) {
                lessonMapGrids[timePoint] = LessonGrid()
            }
            lessonMapGrids[timePoint]?.addLesson(lesson)
        }
    }

    operator fun get(date: Int, time: Int): LessonGrid? {
        val timePoint = TimePoint(date, time)
        return lessonMapGrids[timePoint]
    }

    fun sorted() {
        for ((timePoint, lessonGrid) in lessonMapGrids) {
            lessonGrid.lessons.sortWith(Comparator { p0, p1 -> p1.id.compareTo(p0.id) })
        }
    }

}