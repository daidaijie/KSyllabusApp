package com.daijie.ksyllabusapp.data

/**
 * Created by daidaijie on 17-10-16.
 */
class LessonRecordGrid {

    val lessonRecords: MutableList<LessonRecord> = mutableListOf()

    fun addLessonRecord(lessonRecord: LessonRecord) {
        lessonRecords.add(lessonRecord)
    }

    fun size() = lessonRecords.size
}