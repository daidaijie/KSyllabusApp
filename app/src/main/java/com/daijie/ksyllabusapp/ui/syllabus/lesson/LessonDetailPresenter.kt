package com.daijie.ksyllabusapp.ui.syllabus.lesson

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.ext.onErrorToLogin
import com.daijie.ksyllabusapp.ext.post
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.repository.lesson.LessonDataRepository
import com.daijie.ksyllabusapp.repository.semester.SemesterRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-13.
 */
class LessonDetailPresenter @Inject constructor(
        private val lessonDetailView: LessonDetailContract.View,
        private val userDataRepository: UserDataRepository,
        private val semesterRepository: SemesterRepository,
        private val lessonDataRepository: LessonDataRepository,
        private val subscriptioner: Subscriptioner
) : LessonDetailContract.Presenter, ISubscriptioner by subscriptioner {

    override fun deleteLesson(lesson: Lesson) {
        lessonDetailView.showLoadingDialog("正在从服务器删除课程")
        userDataRepository.currentUser.post(lessonDetailView) { user ->
            lessonDataRepository.deleteLesson(user, semesterRepository.currentSemester, lesson)
                    .to_ui()
                    .onErrorToLogin(lessonDetailView)
                    .subscribe({
                        lessonDetailView.hideLoadingDialog()
                        lessonDetailView.showSuccess("删除成功")
                    }, {
                        it.printStackTrace()
                        Logger.e(it.message)
                        lessonDetailView.hideLoadingDialog()
                        lessonDetailView.showError("删除失败")
                    })
                    .let {
                        add(it)
                    }
        }
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        dispose()
    }
}