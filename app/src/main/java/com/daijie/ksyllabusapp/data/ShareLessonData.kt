package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 2017/10/6.
 */
data class ShareLessonData(
        @SerializedName("account") val account: String,
        @SerializedName("semester") val semester: String,
        @SerializedName("season") val season: String,
        @SerializedName("lessons") val lessons: String
)