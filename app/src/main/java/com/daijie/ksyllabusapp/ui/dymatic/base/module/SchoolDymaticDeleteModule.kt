package com.daijie.ksyllabusapp.ui.dymatic.base.module

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.ui.dymatic.base.delete.SchoolDymaticDeleteContract
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 17-10-28.
 */
@Module
class SchoolDymaticDeleteModule(val view: SchoolDymaticDeleteContract.View) {

    @ActivityScoped
    @Provides
    fun provideView() = view
}