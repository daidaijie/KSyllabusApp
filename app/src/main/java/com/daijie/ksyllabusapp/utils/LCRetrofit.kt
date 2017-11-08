package com.daijie.ksyllabusapp.utils

import com.daijie.ksyllabusapp.App
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by daidaijie on 17-9-28.
 */

object LCRetrofit {

    const val LC_ID = "dfK11nncdLh165E5niCVctsx-gzGzoHsz"
    const val LC_KEY = "yPtWHbESL1XFemOBwEaxjzqD"

    @JvmStatic
    val retrofit: Retrofit by lazy {
        val builder = OkHttpClient.Builder()
                .addInterceptor(ChuckInterceptor(App.context))
                .addInterceptor(HttpLoggingInterceptor())

        Retrofit.Builder()
                .client(builder.build())
                .baseUrl("https://api.leancloud.cn/1.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }
}

