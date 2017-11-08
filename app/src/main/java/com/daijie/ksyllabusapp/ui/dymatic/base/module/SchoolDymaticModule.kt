package com.daijie.ksyllabusapp.ui.dymatic.base.module

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.api.SchoolDymaticApi
import com.daijie.ksyllabusapp.di.qualifier.Remote
import com.daijie.ksyllabusapp.di.qualifier.SchoolRetrofitQualifier
import com.daijie.ksyllabusapp.repository.schooldymatic.SchoolDymaticDataSource
import com.daijie.ksyllabusapp.repository.schooldymatic.remote.SchoolDymaticRemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by daidaijie on 17-10-25.
 */
@Module
class SchoolDymaticModule {

    @Provides
    @ActivityScoped
    fun provideSchoolDymaticApi(@SchoolRetrofitQualifier retrofit: Retrofit)
            = retrofit.create(SchoolDymaticApi::class.java)

    @Provides
    @ActivityScoped
    @Remote
    fun provideSchoolDymaticRemoteDataSource(schoolDymaticApi: SchoolDymaticApi): SchoolDymaticDataSource
            = SchoolDymaticRemoteDataSource(schoolDymaticApi)
}