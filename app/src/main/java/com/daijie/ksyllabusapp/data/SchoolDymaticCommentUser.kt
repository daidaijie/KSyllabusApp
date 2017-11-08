package com.daijie.ksyllabusapp.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 17-10-24.
 */
data class SchoolDymaticCommentUser(
        @SerializedName("account") val account: String,
        @SerializedName("nickname") val nickname: String?,
        @SerializedName("image") val image: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(account)
        parcel.writeString(nickname)
        parcel.writeString(image)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<SchoolDymaticCommentUser> {
        override fun createFromParcel(parcel: Parcel): SchoolDymaticCommentUser {
            return SchoolDymaticCommentUser(parcel)
        }

        override fun newArray(size: Int): Array<SchoolDymaticCommentUser?> {
            return arrayOfNulls(size)
        }
    }
}

val SchoolDymaticCommentUser.name: String
    get() {
        return if (nickname == null || nickname.isEmpty()) {
            account
        } else {
            nickname
        }
    }