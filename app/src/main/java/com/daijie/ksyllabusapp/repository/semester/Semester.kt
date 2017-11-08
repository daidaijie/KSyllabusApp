package com.daijie.ksyllabusapp.repository.semester

/**
 * Created by daidaijie on 17-9-29.
 */
data class Semester(
        val startYear: Int,
        val season: Int
) {
    fun toYears() = "${startYear}-${startYear + 1}"
    fun toRangeYearString() = "$startYear-${startYear + 1}"
    fun toSeasonString() = when (season) {
        1 -> "秋季学期"
        2 -> "春季学期"
        3 -> "夏季学期"
        else -> "未知学期"
    }
}