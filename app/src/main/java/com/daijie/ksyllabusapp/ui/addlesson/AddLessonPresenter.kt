package com.daijie.ksyllabusapp.ui.addlesson

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.LessonTime
import com.daijie.ksyllabusapp.data.TimeRange
import com.daijie.ksyllabusapp.data.isSelected
import com.daijie.ksyllabusapp.ext.*
import com.daijie.ksyllabusapp.repository.lesson.ClassSchedule
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.repository.lesson.LessonDataRepository
import com.daijie.ksyllabusapp.repository.semester.SemesterRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import com.orhanobut.logger.Logger
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/5.
 */
class AddLessonPresenter @Inject constructor(
        private val addLessonView: AddLessonContract.View,
        private val userDataRepository: UserDataRepository,
        private val semesterRepository: SemesterRepository,
        private val lessonDataRepository: LessonDataRepository,
        private val subscriptioner: Subscriptioner
) : AddLessonContract.Presenter, ISubscriptioner by subscriptioner {

    override fun subscribe() {
    }

    override fun postLesson(lessonName: String, lessonTeacher: String, lessonAddress: String,
                            lessonTimes: List<LessonTime>) {
        if (!checkLesson(lessonName, lessonTimes)) {
            return
        }
        val lesson = parseLesson(lessonName, lessonTeacher, lessonAddress, lessonTimes)

        userDataRepository.currentUser.post(addLessonView) { user ->
            addLessonView.showLoadingDialog("正在添加课程至服务器")
            lessonDataRepository.addLesson(user, semesterRepository.currentSemester,
                    listOf(lesson))
                    .delay(500, TimeUnit.MILLISECONDS)
                    .to_ui()
                    .onErrorToLogin(addLessonView)
                    .subscribe({
                        addLessonView.showSuccess("添加成功")
                        addLessonView.hideLoadingDialog()
                    }, {
                        Logger.e("${it.javaClass.name} , ${it.message}")
                        addLessonView.showError(it.message ?: "添加失败")
                        addLessonView.hideLoadingDialog()
                    })
                    .let {
                        add(it)
                    }
        }
    }

    private fun parseLesson(lessonName: String, lessonTeacher: String, lessonAddress: String,
                            lessonTimes: List<LessonTime>, oldLesson: Lesson? = null): Lesson {

        val teacher = if (lessonTeacher.isBlank()) "无" else lessonTeacher
        val address = if (lessonAddress.isBlank()) "无" else lessonAddress

        // 7*13 矩阵 星期*节数，value是周数
        val matrix = MutableList(7, {
            MutableList(13, {
                0
            })
        })

        // 合并所有时间的周数
        for (lessonTime in lessonTimes) {
            val week = lessonTime.weeks.toInt(2)
            for (time in lessonTime.timeRange.startTime..lessonTime.timeRange.endTime) {
                matrix[lessonTime.timeRange.date - 1][time - 1] = matrix[lessonTime.timeRange.date - 1][time - 1] or
                        week
            }
        }

        var startWeek = Int.MAX_VALUE
        var endWeek = Int.MIN_VALUE

        // 将每一天具有相同周数的节数提取出来存入classSchedules中
        val classSchedules = mutableListOf<ClassSchedule>()
        for ((date, dayList) in matrix.withIndex()) {
            val weeksTimeListMap = hashMapOf<Int, MutableList<Int>>()
            for ((index, value) in dayList.withIndex()) {
                if (weeksTimeListMap[value] == null) {
                    weeksTimeListMap[value] = mutableListOf()
                }
                weeksTimeListMap[value]?.add(index + 1)
            }
            for ((weeks, timeList) in weeksTimeListMap) {
                if (weeks == 0) {
                    continue
                }
                val times = timeList.map { time2char(it) }.joinToString("")
                val weekString = pullString(weeks.toString(2))
                startWeek = minOf(weekString.indexOf('1') + 1, startWeek)
                endWeek = maxOf(weekString.lastIndexOf('1') + 1, endWeek)

                val cs = ClassSchedule(times, date + 1, weekString, times)
                classSchedules.add(cs)
            }
        }

        if (oldLesson == null) {
            return Lesson("0", address, false, lessonName, createId().toString(),
                    classSchedules, "${startWeek}-${endWeek}", teacher)
        } else {
            return Lesson(oldLesson.credit, address, oldLesson.fromCreditSystem, lessonName,
                    oldLesson.id, classSchedules, "${startWeek}-${endWeek}", teacher)
        }
    }

    private fun checkLesson(lessonName: String, lessonTimes: List<LessonTime>): Boolean {
        if (lessonName.isEmpty()) {
            addLessonView.showError("请填写课程名")
            return false
        }
        if (lessonTimes.isEmpty()) {
            addLessonView.showError("请添加课程时间")
            return false
        }

        lessonTimes.forEach {
            if (it.weeks.indexOf("1") == -1) {
                addLessonView.showError("请选择周数")
                return false
            }
            if (!it.timeRange.isSelected) {
                addLessonView.showError("请选择上课节数")
                return false
            }
        }
        return true
    }

    override fun updateLesson(lessonName: String, lessonTeacher: String, lessonAddress: String,
                              lessonTimes: List<LessonTime>, oldLesson: Lesson) {
        if (!checkLesson(lessonName, lessonTimes)) {
            return
        }
        val lesson = parseLesson(lessonName, lessonTeacher, lessonAddress, lessonTimes, oldLesson)

        userDataRepository.currentUser.post(addLessonView) { user ->
            addLessonView.showLoadingDialog("正在更新课程至服务器")
            lessonDataRepository.updateLesson(user, semesterRepository.currentSemester,
                    listOf(lesson))
                    .delay(500, TimeUnit.MILLISECONDS)
                    .to_ui()
                    .onErrorToLogin(addLessonView)
                    .subscribe({
                        addLessonView.showSuccess("更新成功")
                        addLessonView.hideLoadingDialog()
                    }, {
                        it.printStackTrace()
                        addLessonView.showError(it.message ?: "更新失败")
                        addLessonView.hideLoadingDialog()
                    })
                    .let {
                        add(it)
                    }
        }
    }

    override fun parseLessonTimeToShow(lesson: Lesson) {
        val lessonTimes = mutableListOf<LessonTime>()
        for (cs in lesson.classSchedule) {
            val timeRanges = parseTime(cs.time)
            for (timeRange in timeRanges) {
                timeRange.date = if (cs.dayInWeek == 0) 7 else cs.dayInWeek
                val lessonTime = LessonTime(cs.weeks, timeRange)
                lessonTimes.add(lessonTime)
            }
        }
        addLessonView.showLessonTime(lessonTimes)
    }

    private fun parseTime(time: String): MutableList<TimeRange> {
        val timeRanges = mutableListOf<TimeRange>()
        var timeRange = TimeRange()
        timeRange.startTime = Int.MIN_VALUE
        timeRange.endTime = Int.MIN_VALUE
        for ((index, c) in time.withIndex()) {
            val currentTime = char2time(c)
            if (timeRange.endTime == Int.MIN_VALUE || currentTime - timeRange.endTime > 1) {
                if (timeRange.endTime != Int.MIN_VALUE) {
                    timeRanges.add(timeRange)
                }
                timeRange = TimeRange()
                timeRange.startTime = currentTime
                timeRange.endTime = currentTime
                if (index == time.length - 1) {
                    timeRanges.add(timeRange)
                }
            } else {
                timeRange.endTime++
                if (index == time.length - 1) {
                    timeRanges.add(timeRange)
                }
            }
        }
        return timeRanges
    }


    override fun unsubscribe() {
        dispose()
    }
}