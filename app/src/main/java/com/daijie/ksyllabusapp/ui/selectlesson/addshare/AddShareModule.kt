package com.daijie.ksyllabusapp.ui.selectlesson.addshare

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 2017/10/7.
 */
@Module
class AddShareModule(val view: AddShareContract.View) {

    @ActivityScoped
    @Provides
    fun provideView() = view

}