package com.daijie.ksyllabusapp.repository.lesson

import android.os.Parcel
import android.os.Parcelable
import com.daijie.ksyllabusapp.data.LessonTime
import com.daijie.ksyllabusapp.ext.*
import com.google.gson.annotations.SerializedName
import com.orhanobut.logger.Logger
import org.greenrobot.greendao.annotation.Transient
import java.lang.StringBuilder
import kotlin.Comparator


/**
 * Created by daidaijie on 17-9-28.
 */
data class Lesson(
        @SerializedName("credit") var credit: String, //2.0
        @SerializedName("room") var room: String, //讲堂五
        @SerializedName("from_credit_system") var fromCreditSystem: Boolean, //true
        @SerializedName("name") var name: String, //[AED6003A]西方古典音乐赏析
        @SerializedName("id") var id: String, //95892
        @SerializedName("class_schedule") var classSchedule: List<ClassSchedule>,
        @SerializedName("duration") var duration: String, //1-16
        @SerializedName("teacher") var teacher: String, //林伟杰
        @SerializedName("isOthersLesson") var isOthersLesson: Boolean = false //林伟杰,
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(ClassSchedule),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(credit)
        parcel.writeString(room)
        parcel.writeByte(if (fromCreditSystem) 1 else 0)
        parcel.writeString(name)
        parcel.writeString(id)
        parcel.writeTypedList(classSchedule)
        parcel.writeString(duration)
        parcel.writeString(teacher)
        parcel.writeByte(if (isOthersLesson) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lesson> {
        override fun createFromParcel(parcel: Parcel): Lesson {
            return Lesson(parcel)
        }

        override fun newArray(size: Int): Array<Lesson?> {
            return arrayOfNulls(size)
        }
    }

    @Transient
    private var _lessonTime: String? = null

    val lessonTime: String
        get() {
            if (_lessonTime == null) {
                _lessonTime = getLessonTime()
            }
            return _lessonTime ?: "无"
        }

    @Transient
    private var _simpleName: String? = null

    val simpleName: String
        get() {
            if (_simpleName == null) {
                _simpleName = getSimpleName()
            }
            return _simpleName ?: ""
        }

    val gridText: String
        get() = "${getSimpleName()}@$room"

}


fun Lesson.getSimpleName(): String {
    val indexOfClassNumber = name.indexOf(']')
    if (indexOfClassNumber != -1) {
        return name.substring(indexOfClassNumber + 1, name.length)
    } else {
        return name
    }
}


fun Lesson.getLessonTime(): String {
    if (classSchedule.isEmpty()) {
        return "无"
    }

    val times = classSchedule.groupBy {
        it.weeks
    }.toSortedMap(Comparator { p0, p1 ->
        return@Comparator p1.toLong().compareTo(p0.toLong())
    })

    val sb = StringBuilder()
    for ((week, timeList) in times) {
        if (timeList.isEmpty()) {
            continue
        }
        val weekString = parseWeekDay(week, ",")
        sb.append(weekString)
        sb.append(" ")
        val chineseSpaceNum = weekString.numOf('周')
        val preSpace = " " * (weekString.length + 1 - chineseSpaceNum) + "　" * chineseSpaceNum
        val sortTimeList = timeList.sortedBy(ClassSchedule::dayInWeek)
        for ((index, time) in sortTimeList.withIndex()) {
            if (index > 0) {
                sb.append(preSpace)
            }
            sb.append(weeks[time.dayInWeek - 1])
            sb.append(time.time)
            sb.append("\n")
        }
    }
    if (sb[sb.length - 1] == '\n') {
        sb.deleteCharAt(sb.length - 1)
    }
    return sb.toString()
}