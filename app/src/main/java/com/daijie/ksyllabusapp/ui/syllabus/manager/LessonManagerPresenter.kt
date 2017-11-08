package com.daijie.ksyllabusapp.ui.syllabus.manager

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.ext.post
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.lesson.LessonDataRepository
import com.daijie.ksyllabusapp.repository.semester.SemesterRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-10.
 */
class LessonManagerPresenter @Inject constructor(
        private val userDataRepository: UserDataRepository,
        private val semesterRepository: SemesterRepository,
        private val lessonDataRepository: LessonDataRepository,
        private val lessonManagerView: LessonManagerContract.View,
        private val subscriptioner: Subscriptioner
) : LessonManagerContract.Presenter, ISubscriptioner by subscriptioner {


    override fun subscribe() {
        userDataRepository.currentUser.post(lessonManagerView) { user ->
            lessonDataRepository.getLessons(user, semesterRepository.currentSemester, false)
                    .to_ui()
                    .subscribe({
                        val lessonMapByType = it.groupBy {
                            it.fromCreditSystem && !it.isOthersLesson
                        }
                        lessonManagerView.showLessons(lessonMapByType[true], lessonMapByType[false])
                    }, {
                        lessonManagerView.showError(it.message ?: "获取课程失败")
                    })
                    .let {
                        add(it)
                    }
        }
    }

    override fun unsubscribe() {
        dispose()
    }
}