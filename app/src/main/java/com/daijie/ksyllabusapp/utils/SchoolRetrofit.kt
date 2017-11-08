package com.daijie.ksyllabusapp.utils

import com.daijie.ksyllabusapp.App
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by daidaijie on 17-9-28.
 */

object SchoolRetrofit {
    @JvmStatic
    val retrofit: Retrofit by lazy {
        val builder = OkHttpClient.Builder()
                .addInterceptor(ChuckInterceptor(App.context))
                .addInterceptor(HttpLoggingInterceptor())
                .addInterceptor { chain ->
                    val request = chain.request()
                    val originalResponse = chain.proceed(request)
                    if (originalResponse.body().contentType().toString() == "application/json") {
                        val json = originalResponse.body().string()
                        var response = json
                        val jsonObj = JSONObject(json)
                        val data = jsonObj.get("data")
                        if (data is JSONArray) {
                            jsonObj.remove("data")
                            val list = JSONObject()
                            list.put("lists", data)
                            jsonObj.put("data", list)
                            response = jsonObj.toString()
                        }
                        return@addInterceptor originalResponse.newBuilder().body(
                                ResponseBody.create(originalResponse.body().contentType(), response)
                        ).build()
                    }
                    originalResponse
                }

        Retrofit.Builder()
                .baseUrl("https://stuapps.com/")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }
}

//class OkHttpIntercept : Interceptor {
//    override fun intercept(chain: Interceptor.Chain?): Response {
//        chain!!
//        val request = chain.request()
//
//        val originalResponse = chain.proceed(request)
////        response.body()
//
//
//        return originalResponse.newBuilder()
//                .body(ResponseBody.create(originalResponse.body().contentType(), "")).build()
//    }
//
//}