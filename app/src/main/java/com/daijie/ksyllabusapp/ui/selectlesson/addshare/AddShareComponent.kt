package com.daijie.ksyllabusapp.ui.selectlesson.addshare

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 2017/10/7.
 */

@ActivityScoped
@Component(modules = arrayOf(AddShareModule::class), dependencies = arrayOf(UserComponent::class))
interface AddShareComponent {

    fun inject(activity: AddShareActivity)
}