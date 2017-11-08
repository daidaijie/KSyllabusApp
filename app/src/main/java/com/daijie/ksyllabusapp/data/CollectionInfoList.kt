package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 2017/10/15.
 */
data class CollectionInfoList(
        @SerializedName("collection_ids") val list: List<CollectionInfo>
)