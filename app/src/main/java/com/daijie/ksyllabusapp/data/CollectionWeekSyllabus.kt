package com.daijie.ksyllabusapp.data

/**
 * Created by daidaijie on 17-10-16.
 */
class CollectionWeekSyllabus {

    private val lessonRecordMapGrids: MutableMap<TimePoint, LessonRecordGrid> = mutableMapOf()

    /**
     * date 星期1 1 星期日 7
     * 时间 1 -> 1,  9 ->9, 0 -> 10  A -> 11 C -> 13
     */
    fun addLessonRecord(lessonRecord: LessonRecord, date: Int, time: Int) {
        var fixDate = date
        if (fixDate == 0) {
            fixDate = 7
        }

        val timePoint = TimePoint(fixDate, time)
        if (lessonRecordMapGrids[timePoint] == null) {
            lessonRecordMapGrids[timePoint] = LessonRecordGrid()
        }
        lessonRecordMapGrids[timePoint]?.addLessonRecord(lessonRecord)
    }

    operator fun get(date: Int, time: Int): LessonRecordGrid? {
        val timePoint = TimePoint(date, time)
        return lessonRecordMapGrids[timePoint]
    }
}