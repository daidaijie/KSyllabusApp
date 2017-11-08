package com.daijie.ksyllabusapp.utils

import com.daijie.ksyllabusapp.data.SemesterSyllabus
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.repository.lessonrule.LessonRule
import com.daijie.ksyllabusapp.repository.semester.Semester

/**
 * Created by daidaijie on 17-9-29.
 */
object Lessons2SyllabusConverter {

    fun convert(semester: Semester, lessons: List<Lesson>, lessonRule: LessonRule): SemesterSyllabus {
        val semesterSyllabus = SemesterSyllabus(semester)
        lessons.forEach { lesson ->
            semesterSyllabus.addLesson(lesson, lessonRule)
        }
        semesterSyllabus.sorted()
        return semesterSyllabus
    }

}