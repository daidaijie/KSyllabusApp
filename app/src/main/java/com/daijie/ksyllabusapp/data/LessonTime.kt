package com.daijie.ksyllabusapp.data

import android.os.Parcel
import android.os.Parcelable
import com.daijie.ksyllabusapp.ext.parseWeekDay
import com.daijie.ksyllabusapp.ext.weeks

/**
 * Created by daidaijie on 2017/10/2.
 */
data class LessonTime(
        var weeks: String = "1111111111111111",
        var timeRange: TimeRange = TimeRange()
)

val LessonTime.lessonWeekString: String
    get() = parseWeekDay(weeks = weeks)

data class TimeRange(
        var date: Int = 0,
        var startTime: Int = 0,
        var endTime: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(date)
        parcel.writeInt(startTime)
        parcel.writeInt(endTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TimeRange> {
        override fun createFromParcel(parcel: Parcel): TimeRange {
            return TimeRange(parcel)
        }

        override fun newArray(size: Int): Array<TimeRange?> {
            return arrayOfNulls(size)
        }
    }
}

val TimeRange.formatText: String
    get() = if (startTime != endTime)
        "${weeks[date - 1]} ${startTime}-${endTime}节"
    else
        "${weeks[date - 1]} 第${startTime}节"

val TimeRange.isSelected
    get() = startTime != 0 && endTime != 0