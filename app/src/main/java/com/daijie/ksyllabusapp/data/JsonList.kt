package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 17-9-29.
 */
data class JsonList<T>(
        @SerializedName("lists") val list: List<T>
)