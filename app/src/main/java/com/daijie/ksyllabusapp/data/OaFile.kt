package com.daijie.ksyllabusapp.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 17-10-21.
 */

data class OaFile(
        @SerializedName("ID") val id: Long,
        @SerializedName("DOCID") val docId: Long,
        @SerializedName("IMAGEFILEID") val imageFileId: Long,
        @SerializedName("IMAGEFILENAME") val imageFileName: String,
        @SerializedName("IMAGEFILEDESC") val imageFileDesc: String,
        @SerializedName("IMAGEFILEWIDTH") val imageFileWidth: Int,
        @SerializedName("IMAGEFILEHEIGHT") val imageFileHeight: Int,
        @SerializedName("DOCFILETYPE") val docFileType: String,
        @SerializedName("VERSIONID") val versionId: Int,
        @SerializedName("VERSIONDETAIL") val versionDetail: String,
        @SerializedName("ISEXTFILE") val isExtFile: String,
        @SerializedName("HASUSEDTEMPLET") val hasUsedTempLet: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(docId)
        parcel.writeLong(imageFileId)
        parcel.writeString(imageFileName)
        parcel.writeString(imageFileDesc)
        parcel.writeInt(imageFileWidth)
        parcel.writeInt(imageFileHeight)
        parcel.writeString(docFileType)
        parcel.writeInt(versionId)
        parcel.writeString(versionDetail)
        parcel.writeString(isExtFile)
        parcel.writeString(hasUsedTempLet)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OaFile> {
        override fun createFromParcel(parcel: Parcel): OaFile {
            return OaFile(parcel)
        }

        override fun newArray(size: Int): Array<OaFile?> {
            return arrayOfNulls(size)
        }
    }
}