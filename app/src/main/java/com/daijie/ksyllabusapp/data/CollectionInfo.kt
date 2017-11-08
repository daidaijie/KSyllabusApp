package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 2017/10/15.
 */
data class CollectionInfo(
        @SerializedName("collection_id") val collectionId: String,
        @SerializedName("season") val season: Int,
        @SerializedName("start_year") val startYear: Int
)