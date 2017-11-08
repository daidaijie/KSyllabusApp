package com.daijie.ksyllabusapp.utils

import com.daijie.ksyllabusapp.App
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by daidaijie on 17-10-29.
 */
object LCImageRetrofit {

    const val LC_ID = "AHV18SMK7h89nXpn6n3XOEmg-gzGzoHsz"
    const val LC_KEY = "QEaE3AH82jNkelSlMPludiin"

    @JvmStatic
    val retrofit: Retrofit by lazy {
        val builder = OkHttpClient.Builder()
                .addInterceptor(ChuckInterceptor(App.context))
                .addInterceptor(HttpLoggingInterceptor())

        Retrofit.Builder()
                .client(builder.build())
                .baseUrl("https://${LC_ID.substring(0, 8)}.api.lncld.net/1.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }
}