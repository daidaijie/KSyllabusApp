package com.daijie.ksyllabusapp.ui.dymatic.base.module

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.ui.dymatic.base.like.SchoolDymaticLikeContract
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 17-10-27.
 */
@Module
class SchoolDymaticLikeModule(private val view: SchoolDymaticLikeContract.View) {

    @ActivityScoped
    @Provides
    fun provideView() = view
}