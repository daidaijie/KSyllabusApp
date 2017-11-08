package com.daijie.ksyllabusapp.ui.selectlesson.sharelist

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 2017/10/7.
 */
@Module
class ShareListModule(private val view: ShareListContract.View) {

    @Provides
    @ActivityScoped
    fun provideView() = view

}