package com.daijie.ksyllabusapp.ui.syllabus.lesson

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 17-10-13.
 */
@ActivityScoped
@Component(modules = arrayOf(LessonDetailModule::class), dependencies = arrayOf(UserComponent::class))
interface LessonDetailComponent {

    fun inject(activity: LessonDetailActivity)
}