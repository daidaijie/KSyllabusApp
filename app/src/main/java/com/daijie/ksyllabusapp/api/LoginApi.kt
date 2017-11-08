package com.daijie.ksyllabusapp.api

import com.daijie.ksyllabusapp.data.JsonResponse
import com.daijie.ksyllabusapp.repository.user.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable
import com.google.gson.annotations.SerializedName


/**
 * Created by daidaijie on 17-9-28.
 */
interface LoginApi {

    @FormUrlEncoded
    @POST("credit/api/v2.1/login")
    fun login(@Field("username") username: String, @Field("password") password: String):
            Observable<JsonResponse<User>>
}


