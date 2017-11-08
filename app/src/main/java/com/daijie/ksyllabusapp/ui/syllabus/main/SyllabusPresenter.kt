package com.daijie.ksyllabusapp.ui.syllabus.main

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.SemesterSyllabus
import com.daijie.ksyllabusapp.ext.from_computation
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.repository.lesson.LessonDataRepository
import com.daijie.ksyllabusapp.repository.lessonrule.LessonRuleDataRepository
import com.daijie.ksyllabusapp.repository.semester.Semester
import com.daijie.ksyllabusapp.repository.semester.SemesterRepository
import com.daijie.ksyllabusapp.repository.user.User
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import com.daijie.ksyllabusapp.utils.DefaultGson
import com.daijie.ksyllabusapp.utils.Lessons2SyllabusConverter
import com.orhanobut.logger.Logger
import rx.Observable
import javax.inject.Inject

/**
 * Created by daidaijie on 17-9-28.
 */
@ActivityScoped
class SyllabusPresenter @Inject constructor(
        private val syllabusView: SyllabusContract.View,
        private val userDataRepository: UserDataRepository,
        private val semesterRepository: SemesterRepository,
        private val lessonDataRepository: LessonDataRepository,
        private val lessonRuleDataRepository: LessonRuleDataRepository,
        private val subscriptioner: Subscriptioner
) : SyllabusContract.Presenter, ISubscriptioner by subscriptioner {

    override var cacheSyllabus: SemesterSyllabus? = null

    override fun getSyllabus(refresh: Boolean) {
        val currentSemester = semesterRepository.currentSemester
        syllabusView.setSemesterText("${currentSemester.toRangeYearString()} ${currentSemester.toSeasonString()}")

        val user = userDataRepository.currentUser
        user?.let {
            syllabusView.setNickname(it.account)
            lessonDataRepository.getLessons(it, currentSemester, refresh)
                    .toSyllabus(user, currentSemester)
                    .to_ui()
                    .handleSyllabus(refresh)
                    .let {
                        add(it)
                    }
        }
    }

    override fun subscribe() {
        getSyllabus(false)
    }

    override fun unsubscribe() {
    }

    private fun Observable<List<Lesson>>.toSyllabus(user: User, currentSemester: Semester)
            = Observable.zip(this, lessonRuleDataRepository.getLessonRules(user, currentSemester),
            { t1, t2 ->
                Lessons2SyllabusConverter.convert(currentSemester, t1, t2)
            })
            .from_computation()


    private fun Observable<SemesterSyllabus>.handleSyllabus(showMsg: Boolean = true) =
            this.to_ui()
                    .subscribe({
                        Logger.d(DefaultGson.gsonForPrint.toJson(it))
                        cacheSyllabus = it
                        SyllabusSender.send(it)
                        if (showMsg) {
                            syllabusView.showSuccess("同步成功")
                        }
                    }, {
                        Logger.e("error: " + it.message)
                        SyllabusSender.send(null)
                        it.printStackTrace()
                        syllabusView.showError(it.message ?: "同步失败")
                    })

}