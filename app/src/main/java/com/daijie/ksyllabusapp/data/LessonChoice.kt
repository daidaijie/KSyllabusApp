package com.daijie.ksyllabusapp.data

import com.daijie.ksyllabusapp.repository.lesson.Lesson

/**
 * Created by daidaijie on 2017/10/6.
 */
data class LessonChoice(
        val lesson: Lesson,
        var isSelected: Boolean = false,
        var isHeader: Boolean = false,
        var isSelectable: Boolean = true
)

val List<LessonChoice>.newGroupByList: List<LessonChoice>
    get() {
        val lessonsGroupByType = this.groupBy {
            it.lesson.fromCreditSystem
        }
        val creditLessons = lessonsGroupByType[true]
        val diyLessons = lessonsGroupByType[false]

        val lessons = mutableListOf<LessonChoice>()
        if (diyLessons != null && diyLessons.isNotEmpty()) {
            diyLessons[0].isHeader = true
            lessons.addAll(diyLessons)
        }
        if (creditLessons != null && creditLessons.isNotEmpty()) {
            creditLessons[0].isHeader = true
            lessons.addAll(creditLessons)
        }
        return lessons
    }