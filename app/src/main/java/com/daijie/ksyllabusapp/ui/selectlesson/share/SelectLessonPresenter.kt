package com.daijie.ksyllabusapp.ui.selectlesson.share

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.LessonChoice
import com.daijie.ksyllabusapp.data.newGroupByList
import com.daijie.ksyllabusapp.ext.post
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.lesson.LessonDataRepository
import com.daijie.ksyllabusapp.repository.semester.SemesterRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/6.
 */
class SelectLessonPresenter @Inject constructor(
        private val userDataRepository: UserDataRepository,
        private val semesterRepository: SemesterRepository,
        private val lessonDataRepository: LessonDataRepository,
        private val selectLessonView: SelectLessonContract.View,
        private val subscriptioner: Subscriptioner
) : SelectLessonContract.Presenter, ISubscriptioner by subscriptioner {

    override fun shareLessons(lessonChoices: List<LessonChoice>) {

        val shareLessons = lessonChoices.filter {
            it.isSelected
        }.map {
            it.lesson
        }

        if (shareLessons.isNotEmpty()) {
            selectLessonView.showLoadingDialog("正在上传分享课程至服务器")
            userDataRepository.currentUser.post(selectLessonView) { user ->
                lessonDataRepository.shareLesson(user, semesterRepository.currentSemester, shareLessons)
                        .delay(500, TimeUnit.MILLISECONDS)
                        .to_ui()
                        .subscribe({
                            selectLessonView.showSuccess("上传成功")
                            selectLessonView.hideLoadingDialog()
                        }, {
                            selectLessonView.showError(it.message ?: "上传失败")
                            selectLessonView.hideLoadingDialog()
                        })
                        .let {
                            add(it)
                        }
            }
        } else {
            selectLessonView.showError("请至少选择一项")
        }
    }

    override fun subscribe() {
        userDataRepository.currentUser.post(selectLessonView) { user ->
            lessonDataRepository.getLessons(user,
                    semesterRepository.currentSemester, false)
                    .to_ui()
                    .map {
                        it.map {
                            LessonChoice(it)
                        }
                    }
                    .subscribe({
                        selectLessonView.showLessonList(it.newGroupByList)
                    }, {
                        selectLessonView.showError(it.message ?: "获取课程失败")
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