package com.daijie.ksyllabusapp.repository

import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.repository.semester.Semester

/**
 * Created by daidaijie on 2017/10/9.
 */
data class SemesterLessons(
        val semester: Semester,
        val lessons: List<Lesson>
)