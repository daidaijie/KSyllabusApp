package com.daijie.ksyllabusapp.user

import cn.edu.stu.syllabus.di.scope.UserScoped
import com.daijie.ksyllabusapp.BaseAppProvider
import com.daijie.ksyllabusapp.repository.lesson.LessonDataRepository
import com.daijie.ksyllabusapp.repository.lessonrule.LessonRuleDataRepository
import com.daijie.ksyllabusapp.repository.semester.SemesterRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import dagger.Subcomponent

/**
 * Created by daidaijie on 17-9-28.
 */

@UserScoped
@Subcomponent(modules = arrayOf(UserModule::class))
interface UserComponent : BaseAppProvider {
    fun userDataRepository(): UserDataRepository
    fun semesterDataRepository(): SemesterRepository
    fun lessonDataRepository(): LessonDataRepository
    fun lessonRuleDataRepository(): LessonRuleDataRepository
}