package com.daijie.ksyllabusapp.ui.selectlesson.sharelist

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.ext.post
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.lesson.LessonDataRepository
import com.daijie.ksyllabusapp.repository.semester.SemesterRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/7.
 */
class ShareListPresenter @Inject constructor(
        private val shareListView: ShareListContract.View,
        private val userDataRepository: UserDataRepository,
        private val semesterRepository: SemesterRepository,
        private val lessonDataRepository: LessonDataRepository,
        private val subscriptioner: Subscriptioner
) : ShareListContract.Presenter, ISubscriptioner by subscriptioner {

    override fun subscribe() {
        loadRecord()
    }

    override fun loadRecord() {
        userDataRepository.currentUser.post(shareListView) { user ->
            lessonDataRepository.getShareLessonRecords(user, semesterRepository.currentSemester)
                    .subscribe({
                        if (it.isNotEmpty()) {
                            shareListView.showShareList(it)
                        }
                    }, {
                        shareListView.showError(it.message ?: "获取失败")
                    })
                    .let {
                        add(it)
                    }
        }
    }

    override fun deleteRecord(objectsId: String, onDeleteSuccess: () -> Unit) {
        shareListView.showLoadingDialog("正在从服务器删除分享的课程")
        lessonDataRepository.deleteShareLessonsById(objectsId)
                .to_ui()
                .subscribe({
                }, {
                    shareListView.showError("删除失败")
                    shareListView.hideLoadingDialog()
                }, {
                    onDeleteSuccess()
                    shareListView.hideLoadingDialog()
                })
                .let {
                    add(it)
                }
    }

    override fun unsubscribe() {
        subscriptioner.dispose()
    }
}