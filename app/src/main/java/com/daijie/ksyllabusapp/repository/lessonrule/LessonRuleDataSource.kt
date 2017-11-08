package com.daijie.ksyllabusapp.repository.lessonrule

import com.daijie.ksyllabusapp.repository.semester.Semester
import com.daijie.ksyllabusapp.repository.user.User
import rx.Observable

/**
 * Created by daidaijie on 17-10-17.
 */
interface LessonRuleDataSource {

    fun getLessonRules(user: User, semester: Semester): Observable<LessonRule>

    fun saveLessonRules(user: User, semester: Semester, lessonRule: LessonRule)
}