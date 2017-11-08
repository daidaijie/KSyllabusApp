package com.daijie.ksyllabusapp.ui.selectlesson.share

import cn.edu.stu.syllabus.di.scope.FragmentScoped
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 2017/10/6.
 */
@Module
class SelectLessonModule(val view: SelectLessonContract.View) {

    @FragmentScoped
    @Provides
    fun provideView() = view
}