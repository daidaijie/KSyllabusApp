package com.daijie.ksyllabusapp.ui.syllabus.main

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 17-9-28.
 */
@Module
class SyllabusModule(
        val view: SyllabusContract.View
) {
    @Provides
    @ActivityScoped
    fun provideView() = view
}