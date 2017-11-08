package com.daijie.ksyllabusapp

import com.daijie.ksyllabusapp.di.qualifier.LCImageRetrofitQualifier
import com.daijie.ksyllabusapp.di.qualifier.LCRetrofitQualifier
import com.daijie.ksyllabusapp.di.qualifier.OARetrofitQualifier
import com.daijie.ksyllabusapp.di.qualifier.SchoolRetrofitQualifier
import com.daijie.ksyllabusapp.utils.LCImageRetrofit
import com.daijie.ksyllabusapp.utils.LCRetrofit
import com.daijie.ksyllabusapp.utils.OARetrofit
import com.daijie.ksyllabusapp.utils.SchoolRetrofit
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by daidaijie on 17-9-28.
 */
@Module
class RetrofitModule {

    @SchoolRetrofitQualifier
    @Provides
    @Singleton
    fun provideSchoolRetrofit(): Retrofit = SchoolRetrofit.retrofit

    @LCRetrofitQualifier
    @Provides
    @Singleton
    fun provideLCRetrofit(): Retrofit = LCRetrofit.retrofit

    @OARetrofitQualifier
    @Provides
    @Singleton
    fun provideOARetrofit(): Retrofit = OARetrofit.retrofit


    @LCImageRetrofitQualifier
    @Provides
    @Singleton
    fun provideLCImageRetrofit(): Retrofit = LCImageRetrofit.retrofit
}