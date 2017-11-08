package com.daijie.ksyllabusapp.ui.addlesson

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 2017/10/5.
 */
@ActivityScoped
@Component(modules = arrayOf(AddLessonModule::class), dependencies = arrayOf(UserComponent::class))
interface AddLessonComponent {

    fun inject(activity: AddLessonActivity)
}