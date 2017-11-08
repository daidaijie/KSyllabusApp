package com.daijie.ksyllabusapp.ui.syllabus.lesson

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 17-10-13.
 */
@Module
class LessonDetailModule(val view: LessonDetailContract.View) {

    @Provides
    @ActivityScoped
    fun provideView() = view
}