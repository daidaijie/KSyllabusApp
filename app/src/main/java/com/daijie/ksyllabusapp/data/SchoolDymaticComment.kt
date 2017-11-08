package com.daijie.ksyllabusapp.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 17-10-24.
 */
data class SchoolDymaticComment(
        @SerializedName("comment") val comment: String,
        @SerializedName("post_time") val postTime: String,
        @SerializedName("uid") val uid: Int,
        @SerializedName("post_id") val postId: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("user") val user: SchoolDymaticCommentUser
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readParcelable(SchoolDymaticCommentUser::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(comment)
        parcel.writeString(postTime)
        parcel.writeInt(uid)
        parcel.writeInt(postId)
        parcel.writeInt(id)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SchoolDymaticComment> {
        override fun createFromParcel(parcel: Parcel): SchoolDymaticComment {
            return SchoolDymaticComment(parcel)
        }

        override fun newArray(size: Int): Array<SchoolDymaticComment?> {
            return arrayOfNulls(size)
        }
    }
}

