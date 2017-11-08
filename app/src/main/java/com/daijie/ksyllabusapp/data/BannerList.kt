package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 2017/10/18.
 */
data class BannerList(
        @SerializedName("timestamp") val timestamp: Long,
        @SerializedName("notifications") val banners: List<Banner>
)