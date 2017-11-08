package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 2017/10/15.
 */
data class CollectionRecord(
        @SerializedName("id") val id: String,
        @SerializedName("account") val account: String,
        @SerializedName("syllabus") val syllabus: String
)