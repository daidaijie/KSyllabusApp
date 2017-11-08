package com.daijie.ksyllabusapp.ui.selectlesson.sendsyllabus

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.LessonChoice
import com.daijie.ksyllabusapp.data.newGroupByList
import com.daijie.ksyllabusapp.ext.*
import com.daijie.ksyllabusapp.repository.lesson.LessonDataRepository
import com.daijie.ksyllabusapp.repository.semester.SemesterRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/15.
 */
class SendSyllabusPresenter @Inject constructor(
        private val userDataRepository: UserDataRepository,
        private val semesterRepository: SemesterRepository,
        private val lessonDataRepository: LessonDataRepository,
        private val sendSyllabusView: SendSyllabusContract.View,
        private val subscriptioner: Subscriptioner
) : SendSyllabusContract.Presenter, ISubscriptioner by subscriptioner {

    override fun subscribe() {
        userDataRepository.currentUser.post(sendSyllabusView) { user ->
            lessonDataRepository.getLessons(user, semesterRepository.currentSemester, false)
                    .to_ui()
                    .map {
                        it.map {
                            LessonChoice(it, it.fromCreditSystem)
                        }
                    }
                    .subscribe({
                        sendSyllabusView.showLessonChoice(it.newGroupByList)
                    }, {
                        sendSyllabusView.showError("课程获取失败")
                    })
                    .let {
                        add(it)
                    }
        }
    }

    override fun unsubscribe() {
        dispose()
    }

    override fun sendLessons(collectionId: String, lessonChoices: List<LessonChoice>) {
        val lessons = lessonChoices
                .filter {
                    it.isSelected
                }
                .map {
                    it.lesson
                }
        if (lessons.isNotEmpty()) {
            sendSyllabusView.showLoadingDialog("正在上传课表至服务器")
            userDataRepository.currentUser.post(sendSyllabusView) { user ->
                lessonDataRepository.sendSyllabus(user, semesterRepository.currentSemester, collectionId, lessons)
                        .to_ui()
                        .onErrorToLogin(sendSyllabusView)
                        .subscribe({
                            sendSyllabusView.hideLoadingDialog()
                            sendSyllabusView.showSuccess("上传成功")
                        }, {
                            Logger.e(it.message)
                            sendSyllabusView.hideLoadingDialog()
                            sendSyllabusView.showError("上传失败")
                        })
            }
        } else {

        }


    }
}