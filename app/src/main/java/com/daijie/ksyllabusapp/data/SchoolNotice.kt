package com.daijie.ksyllabusapp.data

import android.os.Parcel
import android.os.Parcelable
import com.daijie.ksyllabusapp.ext.fromJson
import com.daijie.ksyllabusapp.utils.DefaultGson
import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 2017/10/22.
 */

data class SchoolNotice(
        @SerializedName("post_type") val postType: Int, //2
        @SerializedName("comments") var comments: List<SchoolDymaticComments>?,
        @SerializedName("user") val schoolDymaticUser: SchoolDymaticUser,
        @SerializedName("id") val id: Int, //1918
        @SerializedName("activity_start_time") val activityStartTime: String?, //null
        @SerializedName("content") val content: String,
        @SerializedName("thumb_ups") var schoolDymaticThumbUps: MutableList<SchoolDymaticThumbUp>?,
        @SerializedName("activity_end_time") val activityEndTime: String?, //null
        @SerializedName("post_time") val postTime: String, //2017-10-13 00:30:28
        @SerializedName("photo_list_json") val photoListJson: String?, //null
        @SerializedName("source") val source: String, //汕大课程表
        @SerializedName("description") val description: String, //晚安😁
        @SerializedName("activity_location") val activityLocation: String? //未指定
) : Parcelable {

    private var _photos: List<SchoolDymaticPhotoInfo>? = null

    val photos: List<SchoolDymaticPhotoInfo>
        get() {
            return _photos.let {
                if (it == null) {
                    _photos = geDymaticPhotos()
                }
                _photos!!
            }
        }

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.createTypedArrayList(SchoolDymaticComments),
            parcel.readParcelable(SchoolDymaticUser::class.java.classLoader),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(SchoolDymaticThumbUp),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    private fun geDymaticPhotos(): List<SchoolDymaticPhotoInfo> {
        return if (photoListJson == null) {
            listOf()
        } else {
            DefaultGson.gson.fromJson<SchoolDymaticPhotoList?>(photoListJson)?.photos ?: listOf()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(postType)
        parcel.writeTypedList(comments)
        parcel.writeParcelable(schoolDymaticUser, flags)
        parcel.writeInt(id)
        parcel.writeString(activityStartTime)
        parcel.writeString(content)
        parcel.writeTypedList(schoolDymaticThumbUps)
        parcel.writeString(activityEndTime)
        parcel.writeString(postTime)
        parcel.writeString(photoListJson)
        parcel.writeString(source)
        parcel.writeString(description)
        parcel.writeString(activityLocation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SchoolNotice> {
        override fun createFromParcel(parcel: Parcel): SchoolNotice {
            return SchoolNotice(parcel)
        }

        override fun newArray(size: Int): Array<SchoolNotice?> {
            return arrayOfNulls(size)
        }
    }


}

