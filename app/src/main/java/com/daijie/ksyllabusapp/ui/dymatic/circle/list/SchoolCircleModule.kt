package com.daijie.ksyllabusapp.ui.dymatic.circle.list

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.api.SchoolDymaticApi
import com.daijie.ksyllabusapp.di.qualifier.Remote
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticDeleteModule
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticLikeModule
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticModule
import com.daijie.ksyllabusapp.repository.schoolcircle.SchoolCircleDataSource
import com.daijie.ksyllabusapp.repository.schoolcircle.remote.SchoolCircleRemoteDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 17-10-22.
 */
@Module(includes = arrayOf(SchoolDymaticModule::class, SchoolDymaticLikeModule::class, SchoolDymaticDeleteModule::class))
class SchoolCircleModule(val view: SchoolCircleContract.View) {

    @Provides
    @ActivityScoped
    fun provideView() = view

    @Provides
    @ActivityScoped
    @Remote
    fun provideSchoolCircleRemoteDataSource(schoolDymaticApi: SchoolDymaticApi): SchoolCircleDataSource
            = SchoolCircleRemoteDataSource(schoolDymaticApi)


}