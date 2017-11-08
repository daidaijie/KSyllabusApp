package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 17-10-24.
 */
data class SchoolNoticeRequest(
        @SerializedName("source") val source: String,
        @SerializedName("uid") val uid: Int,
        @SerializedName("token") val token: String,
        @SerializedName("description") val content: String,
        @SerializedName("content") var url: String = "",
        @SerializedName("photo_list_json") var photosJson: String? = null,
        @SerializedName("activity_start_time") var activityStartTime: Long? = null,
        @SerializedName("activity_end_time") var activityEndTime: Long? = null,
        @SerializedName("activity_location") var activityLocation: String = "未指定",
        @SerializedName("post_type") var postType: Int = 2
)