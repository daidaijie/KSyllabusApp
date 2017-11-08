package com.daijie.ksyllabusapp.littlevp.uploadimage

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.api.LCUploadImageApi
import com.daijie.ksyllabusapp.di.qualifier.LCImageRetrofitQualifier
import com.daijie.ksyllabusapp.di.qualifier.Remote
import com.daijie.ksyllabusapp.repository.lcimage.LCImageDataSource
import com.daijie.ksyllabusapp.repository.lcimage.remote.LCImageRemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by daidaijie on 17-10-30.
 */
@Module
class UploadImageModule(val view: UploadImageContract.View) {

    @ActivityScoped
    @Provides
    fun provideView() = view

    @Provides
    @ActivityScoped
    fun provideLCUploadImageApi(@LCImageRetrofitQualifier retrofit: Retrofit) = retrofit.create(LCUploadImageApi::class.java)

    @Provides
    @ActivityScoped
    @Remote
    fun provideLCImageRemoteDataSource(lcUploadImageApi: LCUploadImageApi): LCImageDataSource
            = LCImageRemoteDataSource(lcUploadImageApi)
}