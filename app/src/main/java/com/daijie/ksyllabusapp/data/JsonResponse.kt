package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 17-9-28.
 */
data class JsonResponse<T>(
        @SerializedName("code") val code: Int,
        @SerializedName("data") val data: T,
        @SerializedName("message") val message: String
)

val JsonResponse<*>.isSuccess: Boolean
    get() = message == "success" || (message == "fail" && code == 201)