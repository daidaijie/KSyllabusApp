package com.daijie.ksyllabusapp.ui.oa.detail

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.api.OaApi
import com.daijie.ksyllabusapp.data.OaInfo
import com.daijie.ksyllabusapp.di.qualifier.OARetrofitQualifier
import com.daijie.ksyllabusapp.di.qualifier.Remote
import com.daijie.ksyllabusapp.repository.oafile.OaFileDataSource
import com.daijie.ksyllabusapp.repository.oafile.remote.OaFileRemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by daidaijie on 17-10-21.
 */
@Module
class OADetailModule(val view: OADetailContract.View, val oaInfo: OaInfo) {

    @Provides
    @ActivityScoped
    fun provideView() = view

    @Provides
    @ActivityScoped
    fun provideOaInfo() = oaInfo

    @Provides
    @ActivityScoped
    fun provideOaApi(@OARetrofitQualifier retrofit: Retrofit) = retrofit.create(OaApi::class.java)

    @Provides
    @Remote
    fun provideOaFileRemoteDataSource(oaApi: OaApi): OaFileDataSource = OaFileRemoteDataSource(oaApi)
}