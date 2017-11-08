package com.daijie.ksyllabusapp.ui.selectlesson.collectionlist

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.ext.onErrorToLogin
import com.daijie.ksyllabusapp.ext.post
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.lesson.LessonDataRepository
import com.daijie.ksyllabusapp.repository.semester.SemesterRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/15.
 */
class CollectionListPresenter @Inject constructor(
        private val userDataRepository: UserDataRepository,
        private val semesterRepository: SemesterRepository,
        private val lessonDataRepository: LessonDataRepository,
        private val collectionListView: CollectionListContract.View,
        private val subscriptioner: Subscriptioner
) : CollectionListContract.Presenter, ISubscriptioner by subscriptioner {

    override fun subscribe() {
        loadCollectionList()
    }

    override fun loadCollectionList() {
        userDataRepository.currentUser.post(collectionListView) { user ->
            collectionListView.showLoadingDialog("正在从服务器上获取课程收集列表")
            lessonDataRepository.getCollectionLessonList(user, semesterRepository.currentSemester)
                    .to_ui()
                    .onErrorToLogin(collectionListView)
                    .subscribe({
                        collectionListView.showSuccess("获取成功")
                        collectionListView.showCollectionList(it)
                        collectionListView.hideLoadingDialog()
                    }, {
                        collectionListView.showError(it.message ?: "获取失败")
                        collectionListView.hideLoadingDialog()
                    })
                    .let {
                        add(it)
                    }
        }
    }

    override fun addCollection() {
        userDataRepository.currentUser.post(collectionListView) { user ->
            collectionListView.showLoadingDialog("正在添加课表收集请求至服务器")
            lessonDataRepository.applyCollectionLesson(user, semesterRepository.currentSemester)
                    .to_ui()
                    .onErrorToLogin(collectionListView)
                    .subscribe({
                        collectionListView.addCollection(it)
                        collectionListView.showSuccess("申请成功")
                        collectionListView.hideLoadingDialog()
                    }, {
                        collectionListView.showError(it.message ?: "申请失败")
                        collectionListView.hideLoadingDialog()
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