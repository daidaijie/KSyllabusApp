package com.daijie.ksyllabusapp.repository.lessonrule

import com.daijie.ksyllabusapp.data.TimePoint

/**
 * Created by daidaijie on 17-10-17.
 */
class LessonRule {

    private val rules = mutableMapOf<TimePoint, List<Long>>()

    operator fun get(date: Int, time: Int) = rules[TimePoint(date, time)]

    operator fun set(date: Int, time: Int, rule: List<Long>) {
        rules[TimePoint(date, time)] = rule
    }
}