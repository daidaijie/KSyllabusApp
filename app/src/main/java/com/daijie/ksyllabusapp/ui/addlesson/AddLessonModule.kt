package com.daijie.ksyllabusapp.ui.addlesson

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 2017/10/5.
 */
@Module
class AddLessonModule(val view: AddLessonContract.View) {

    @ActivityScoped
    @Provides
    fun provideView() = view
}