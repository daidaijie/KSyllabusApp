package com.daijie.ksyllabusapp.ui.syllabus.manager

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 17-10-10.
 */
@ActivityScoped
@Component(modules = arrayOf(LessonManagerModule::class), dependencies = arrayOf(UserComponent::class))
interface LessonManagerComponent {
    fun inject(activity: LessonsManagerActivity)
}