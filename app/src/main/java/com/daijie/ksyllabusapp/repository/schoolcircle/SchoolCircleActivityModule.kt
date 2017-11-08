package com.daijie.ksyllabusapp.repository.schoolcircle

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.api.SchoolDymaticApi
import com.daijie.ksyllabusapp.di.qualifier.Remote
import com.daijie.ksyllabusapp.di.qualifier.SchoolRetrofitQualifier
import com.daijie.ksyllabusapp.repository.schoolcircle.remote.SchoolCircleRemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by daidaijie on 2017/11/7.
 */
@Module
class SchoolCircleActivityModule {

    @Provides
    @ActivityScoped
    fun provideSchoolDymaticApi(@SchoolRetrofitQualifier retrofit: Retrofit)
            = retrofit.create(SchoolDymaticApi::class.java)

    @Provides
    @ActivityScoped
    @Remote
    fun provideSchoolCircleRemoteDataSource(schoolDymaticApi: SchoolDymaticApi): SchoolCircleDataSource
            = SchoolCircleRemoteDataSource(schoolDymaticApi)
}