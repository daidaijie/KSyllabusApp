package com.daijie.ksyllabusapp.repository.lesson

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by daidaijie on 17-9-28.
 */
data class ClassSchedule(
        @SerializedName("original_time") var originalTime: String, //89
        @SerializedName("day_in_week") var dayInWeek: Int, //1
        @SerializedName("weeks") var weeks: String, //1111111111111111
        @SerializedName("time") var time: String //89
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(originalTime)
        parcel.writeInt(dayInWeek)
        parcel.writeString(weeks)
        parcel.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ClassSchedule> {
        override fun createFromParcel(parcel: Parcel): ClassSchedule {
            return ClassSchedule(parcel)
        }

        override fun newArray(size: Int): Array<ClassSchedule?> {
            return arrayOfNulls(size)
        }
    }
}