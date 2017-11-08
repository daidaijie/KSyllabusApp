package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 2017/11/7.
 */
data class LCFile(
        @SerializedName("objectId")   val objectId: String,
        @SerializedName("createAt")val createAt: String,
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String,
        @SerializedName("provider") val provider: String,
        @SerializedName("bucket") val bucket: String,
        @SerializedName("size") val size: Long
)