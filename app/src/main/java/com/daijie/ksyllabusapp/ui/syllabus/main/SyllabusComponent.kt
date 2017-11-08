package com.daijie.ksyllabusapp.ui.syllabus.main

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 17-9-28.
 */
@ActivityScoped
@Component(modules = arrayOf(SyllabusModule::class), dependencies = arrayOf(UserComponent::class))
interface SyllabusComponent {
    fun inject(activity: SyllabusActivity)
}