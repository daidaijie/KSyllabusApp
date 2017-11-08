package com.daijie.ksyllabusapp.ui.main

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.api.BannerApi
import com.daijie.ksyllabusapp.di.qualifier.Local
import com.daijie.ksyllabusapp.di.qualifier.Remote
import com.daijie.ksyllabusapp.di.qualifier.SchoolRetrofitQualifier
import com.daijie.ksyllabusapp.repository.banner.BannerDataSource
import com.daijie.ksyllabusapp.repository.banner.local.BannerLocalDataSource
import com.daijie.ksyllabusapp.repository.banner.remote.BannerRemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by daidaijie on 2017/10/18.
 */
@Module
class MainModule(val view: MainContract.View) {

    @Provides
    @ActivityScoped
    fun provideView() = view

    @Provides
    @ActivityScoped
    @Local
    fun provieBannerLocalDataSource(): BannerDataSource = BannerLocalDataSource()

    @Provides
    @ActivityScoped
    fun provideBannerApi(@SchoolRetrofitQualifier retrofit: Retrofit) = retrofit.create(BannerApi::class.java)

    @Provides
    @ActivityScoped
    @Remote
    fun provieBannerRemoteDataSource(bannerApi: BannerApi): BannerDataSource = BannerRemoteDataSource(bannerApi)

}