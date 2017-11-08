package com.daijie.ksyllabusapp.ui.login

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 17-9-28.
 */
@Module
class LoginModule(val view: LoginContract.View, val isFirst: Boolean) {

    @Provides
    @ActivityScoped
    fun provideView() = view

    @Provides
    @ActivityScoped
    fun provideFirst() = isFirst
}