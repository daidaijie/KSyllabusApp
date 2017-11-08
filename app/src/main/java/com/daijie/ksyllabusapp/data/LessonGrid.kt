package com.daijie.ksyllabusapp.data

import com.daijie.ksyllabusapp.repository.lesson.Lesson

/**
 * Created by daidaijie on 17-9-29.
 */
class LessonGrid {
    val lessons: MutableList<Lesson> = mutableListOf()

    fun addLesson(lesson: Lesson) {
        lessons.add(lesson)
    }

    fun isSame(otherLessons: LessonGrid?): Boolean = if (otherLessons == null) false else (
            lessons.isNotEmpty() && otherLessons.lessons.isNotEmpty()
                    && lessons[0] == otherLessons.lessons[0])

    operator fun get(index: Int): Lesson = lessons[index]
}

