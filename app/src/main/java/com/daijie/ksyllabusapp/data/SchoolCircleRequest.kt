package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 17-10-24.
 */
data class SchoolCircleRequest(
        @SerializedName("content") val content: String,
        @SerializedName("source") val source: String,
        @SerializedName("uid") var uid: Int,
        @SerializedName("token") var token: String,
        @SerializedName("photo_list_json") var photosJson: String? = null,
        @SerializedName("description") var description: String = "None",
        @SerializedName("post_type") val postType: Int = 0
)