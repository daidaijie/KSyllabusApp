package com.daijie.ksyllabusapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 17-10-22.
 */
data class SchoolDymaticPhotoList(
        @SerializedName("photo_list") val photos: List<SchoolDymaticPhotoInfo>
)