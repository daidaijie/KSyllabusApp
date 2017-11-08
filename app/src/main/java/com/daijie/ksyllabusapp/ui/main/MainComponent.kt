package com.daijie.ksyllabusapp.ui.main

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 2017/10/18.
 */
@ActivityScoped
@Component(modules = arrayOf(MainModule::class),dependencies = arrayOf(UserComponent::class))
interface MainComponent {

    fun inject(activity: MainActivity)
}