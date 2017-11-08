package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 2017/10/23.
 */
data class ThumbUp(
        @SerializedName("post_id") val postId: Int,
        @SerializedName("uid") val uid: Int,
        @SerializedName("token") val token: String
)