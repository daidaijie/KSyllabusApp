package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 17-10-24.
 */
data class CommentRequest(
        @SerializedName("comment") val comment: String,
        @SerializedName("token") val token: String,
        @SerializedName("post_id") val postId: Int,
        @SerializedName("uid") val userId: Int
)