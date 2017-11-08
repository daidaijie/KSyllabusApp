package com.daijie.ksyllabusapp.repository.lessonrule

import cn.edu.stu.syllabus.di.scope.UserScoped
import com.daijie.ksyllabusapp.di.qualifier.Local
import com.daijie.ksyllabusapp.repository.semester.Semester
import com.daijie.ksyllabusapp.repository.user.User
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-17.
 */
@UserScoped
class LessonRuleDataRepository @Inject constructor(
        @Local private val lessonRuleLocalDataSource: LessonRuleDataSource
) : LessonRuleDataSource {

    override fun getLessonRules(user: User, semester: Semester) =
            lessonRuleLocalDataSource.getLessonRules(user, semester)

    override fun saveLessonRules(user: User, semester: Semester, lessonRule: LessonRule) =
            lessonRuleLocalDataSource.saveLessonRules(user, semester, lessonRule)
}