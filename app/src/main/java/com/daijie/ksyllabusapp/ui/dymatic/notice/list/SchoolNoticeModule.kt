package com.daijie.ksyllabusapp.ui.dymatic.notice.list

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.api.SchoolDymaticApi
import com.daijie.ksyllabusapp.di.qualifier.Remote
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticDeleteModule
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticLikeModule
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticModule
import com.daijie.ksyllabusapp.repository.schoolnotice.SchoolNoticeDataSource
import com.daijie.ksyllabusapp.repository.schoolnotice.remote.SchoolNoticeRemoteDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 2017/10/22.
 */
@Module(includes = arrayOf(SchoolDymaticModule::class, SchoolDymaticLikeModule::class, SchoolDymaticDeleteModule::class))
class SchoolNoticeModule(private val view: SchoolNoticeContract.View) {

    @Provides
    @ActivityScoped
    fun provideView() = view

    @Provides
    @ActivityScoped
    @Remote
    fun provideSchoolNoticeRemoteDataSource(schoolDymaticApi: SchoolDymaticApi): SchoolNoticeDataSource
            = SchoolNoticeRemoteDataSource(schoolDymaticApi)

}