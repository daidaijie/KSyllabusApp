package com.daijie.ksyllabusapp.ui.syllabus.main

import com.daijie.ksyllabusapp.data.SemesterSyllabus

/**
 * Created by daidaijie on 17-9-30.
 */
interface SyllabusReceiver {
    fun onSyllabusReceived(syllabus: SemesterSyllabus?)
}