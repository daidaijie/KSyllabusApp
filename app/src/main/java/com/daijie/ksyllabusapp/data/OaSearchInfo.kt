package com.daijie.ksyllabusapp.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by daidaijie on 2017/10/18.
 */
data class OaSearchInfo(
        var subCompanyId: Int = 0,
        var keyword: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(subCompanyId)
        parcel.writeString(keyword)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OaSearchInfo> {
        override fun createFromParcel(parcel: Parcel): OaSearchInfo {
            return OaSearchInfo(parcel)
        }

        override fun newArray(size: Int): Array<OaSearchInfo?> {
            return arrayOfNulls(size)
        }
    }
}

