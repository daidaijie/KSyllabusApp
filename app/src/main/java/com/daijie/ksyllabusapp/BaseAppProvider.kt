package com.daijie.ksyllabusapp

import com.daijie.ksyllabusapp.di.qualifier.LCImageRetrofitQualifier
import com.daijie.ksyllabusapp.di.qualifier.LCRetrofitQualifier
import com.daijie.ksyllabusapp.di.qualifier.OARetrofitQualifier
import com.daijie.ksyllabusapp.di.qualifier.SchoolRetrofitQualifier
import retrofit2.Retrofit

/**
 * Created by daidaijie on 17-9-28.
 */
interface BaseAppProvider {
    @SchoolRetrofitQualifier
    fun getSchoolRetrofit(): Retrofit

    @LCRetrofitQualifier
    fun getLCRetrofit(): Retrofit

    @OARetrofitQualifier
    fun getOARetrofit(): Retrofit

    @LCImageRetrofitQualifier
    fun getLCImageRetrofitQualifier(): Retrofit
}