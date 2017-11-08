package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 2017/10/15.
 */
data class CollectionRecordList(
        @SerializedName("collections") val collections: List<CollectionRecord>
)