package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 2017/10/18.
 */
data class Banner(
        @SerializedName("id") val id: Int,
        @SerializedName("link") val link: String,
        @SerializedName("description") val description: String,
        @SerializedName("url") val url: String
)