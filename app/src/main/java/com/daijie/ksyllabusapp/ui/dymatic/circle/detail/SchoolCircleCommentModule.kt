package com.daijie.ksyllabusapp.ui.dymatic.circle.detail

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticDeleteModule
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticLikeModule
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticModule
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 17-10-25.
 */
@Module(includes = arrayOf(SchoolDymaticModule::class, SchoolDymaticLikeModule::class, SchoolDymaticDeleteModule::class))
class SchoolCircleCommentModule(val view: SchoolCircleCommentContract.View) {

    @Provides
    @ActivityScoped
    fun provideView() = view
}