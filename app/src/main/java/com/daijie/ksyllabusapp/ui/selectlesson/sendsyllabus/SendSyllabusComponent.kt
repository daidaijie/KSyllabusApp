package com.daijie.ksyllabusapp.ui.selectlesson.sendsyllabus

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 2017/10/15.
 */

@ActivityScoped
@Component(modules = arrayOf(SendSyllabusModule::class), dependencies = arrayOf(UserComponent::class))
interface SendSyllabusComponent {
    fun inject(activity: SendSyllabusActivity)
}