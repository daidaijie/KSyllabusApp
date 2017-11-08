package com.daijie.ksyllabusapp.ui.syllabus.manager

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 17-10-10.
 */
@Module
class LessonManagerModule(val view: LessonManagerContract.View) {

    @Provides
    @ActivityScoped
    fun provideView() = view
}