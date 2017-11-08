package com.daijie.ksyllabusapp.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SchoolDymaticUser(
        @SerializedName("account") val account: String, //14xfdeng
        @SerializedName("image") val image: String?, //http://ow7nw2lyt.bkt.clouddn.com/e854bb770bc44d90a3d308d5dedf6b52.jpeg
        @SerializedName("nickname") val nickname: String?, //xiaofu
        @SerializedName("id") val id: Int //1
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(account)
        parcel.writeString(image)
        parcel.writeString(nickname)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SchoolDymaticUser> {
        override fun createFromParcel(parcel: Parcel): SchoolDymaticUser {
            return SchoolDymaticUser(parcel)
        }

        override fun newArray(size: Int): Array<SchoolDymaticUser?> {
            return arrayOfNulls(size)
        }
    }

    val name: String
        get() {
            return if (nickname.isNullOrEmpty()) {
                account
            } else {
                nickname ?: account
            }
        }
}