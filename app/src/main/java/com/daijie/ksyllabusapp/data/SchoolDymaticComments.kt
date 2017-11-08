package com.daijie.ksyllabusapp.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SchoolDymaticComments(
        @SerializedName("uid") val uid: Int, //576
        @SerializedName("id") val id: Int //4433
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(uid)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SchoolDymaticComments> {
        override fun createFromParcel(parcel: Parcel): SchoolDymaticComments {
            return SchoolDymaticComments(parcel)
        }

        override fun newArray(size: Int): Array<SchoolDymaticComments?> {
            return arrayOfNulls(size)
        }
    }
}