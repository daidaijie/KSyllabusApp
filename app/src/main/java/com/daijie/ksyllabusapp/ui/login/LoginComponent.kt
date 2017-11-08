package com.daijie.ksyllabusapp.ui.login

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 17-9-28.
 */
@ActivityScoped
@Component(modules = arrayOf(LoginModule::class), dependencies = arrayOf(UserComponent::class))
interface LoginComponent {
    fun inject(activity: LoginActivity)
}