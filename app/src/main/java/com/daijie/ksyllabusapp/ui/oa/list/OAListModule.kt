package com.daijie.ksyllabusapp.ui.oa.list

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.api.OaApi
import com.daijie.ksyllabusapp.data.OaSearchInfo
import com.daijie.ksyllabusapp.di.qualifier.OARetrofitQualifier
import com.daijie.ksyllabusapp.di.qualifier.Remote
import com.daijie.ksyllabusapp.repository.oa.OADataSource
import com.daijie.ksyllabusapp.repository.oa.remote.OARemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by daidaijie on 2017/10/18.
 */
@Module
class OAListModule(private val view: OAListContract.View, private val oaSearchInfo: OaSearchInfo) {

    @Provides
    @ActivityScoped
    fun provideView() = view

    @ActivityScoped
    @Provides
    fun provideOASearchInfo() = oaSearchInfo

    @Provides
    @ActivityScoped
    fun provideOaApi(@OARetrofitQualifier retrofit: Retrofit) = retrofit.create(OaApi::class.java)

    @ActivityScoped
    @Remote
    @Provides
    fun provideOARemoteDataSource(oaApi: OaApi): OADataSource = OARemoteDataSource(oaApi)

}