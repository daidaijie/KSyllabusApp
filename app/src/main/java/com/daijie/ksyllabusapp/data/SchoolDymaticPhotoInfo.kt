package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

data class SchoolDymaticPhotoInfo(
        @SerializedName("size_big") val bigImg: String,
        @SerializedName("size_small") val smallImg: String
)