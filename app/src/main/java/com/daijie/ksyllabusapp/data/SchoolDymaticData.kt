package com.daijie.ksyllabusapp.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by daidaijie on 2017/10/23.
 */
data class SchoolDymaticData(
        val schoolNotice: SchoolNotice,
        val isMyPost: Boolean,
        val myLevel: Int,
        var isMyLike: Boolean
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readParcelable(SchoolNotice::class.java.classLoader),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(schoolNotice, flags)
        parcel.writeByte(if (isMyPost) 1 else 0)
        parcel.writeInt(myLevel)
        parcel.writeByte(if (isMyLike) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SchoolDymaticData> {
        override fun createFromParcel(parcel: Parcel): SchoolDymaticData {
            return SchoolDymaticData(parcel)
        }

        override fun newArray(size: Int): Array<SchoolDymaticData?> {
            return arrayOfNulls(size)
        }
    }
}