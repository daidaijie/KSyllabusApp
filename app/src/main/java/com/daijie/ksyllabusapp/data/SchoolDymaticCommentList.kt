package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 17-10-24.
 */
data class SchoolDymaticCommentList(
        @SerializedName("comments") val comments: List<SchoolDymaticComment>
)