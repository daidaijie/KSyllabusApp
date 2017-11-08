package com.daijie.ksyllabusapp.ui.syllabus.main

import com.daijie.ksyllabusapp.data.SemesterSyllabus

/**
 * Created by daidaijie on 17-9-30.
 */
object SyllabusSender {

    private val observers = mutableListOf<SyllabusReceiver>()

    fun register(receiver: SyllabusReceiver) {
        observers.add(receiver)
    }

    fun unregister(receiver: SyllabusReceiver) {
        observers.remove(receiver)
    }

    fun send(syllabus: SemesterSyllabus?) {
        observers.forEach {
            it.onSyllabusReceived(syllabus)
        }
    }

}