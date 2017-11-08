package com.daijie.ksyllabusapp.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by daidaijie on 17-10-24.
 */
data class SchoolDymaticCommentData(
        val schoolDymaticComment: SchoolDymaticComment,
        var isMyComment: Boolean,
        var isCommentSuccess: Boolean = true
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readParcelable(SchoolDymaticComment::class.java.classLoader),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(schoolDymaticComment, flags)
        parcel.writeByte(if (isMyComment) 1 else 0)
        parcel.writeByte(if (isCommentSuccess) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SchoolDymaticCommentData> {
        override fun createFromParcel(parcel: Parcel): SchoolDymaticCommentData {
            return SchoolDymaticCommentData(parcel)
        }

        override fun newArray(size: Int): Array<SchoolDymaticCommentData?> {
            return arrayOfNulls(size)
        }
    }
}