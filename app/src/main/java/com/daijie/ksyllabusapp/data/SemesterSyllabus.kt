package com.daijie.ksyllabusapp.data

import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.repository.lessonrule.LessonRule
import com.daijie.ksyllabusapp.repository.semester.Semester

/**
 * Created by daidaijie on 17-9-29.
 */
class SemesterSyllabus(val semester: Semester) {

    private val weekSyllabusMap: MutableMap<Int, WeekSyllabus> = mutableMapOf()

    fun addLesson(lesson: Lesson, lessonRule: LessonRule) {
        lesson.classSchedule.forEach { lessonTime ->
            for ((week, hasClass) in lessonTime.weeks.withIndex()) {
                if (hasClass == '1') {
                    if (weekSyllabusMap[week] == null) {
                        weekSyllabusMap[week] = WeekSyllabus()
                    }
                    weekSyllabusMap[week]?.addLesson(lesson, lessonTime.dayInWeek, lessonTime.time, lessonRule)
                }
            }
        }
    }

    fun sorted() {
        for ((week, weekSyllabus) in weekSyllabusMap) {
            weekSyllabus.sorted()
        }
    }

    operator fun get(index: Int): WeekSyllabus? = weekSyllabusMap[index]

}