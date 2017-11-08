package com.daijie.ksyllabusapp.ui.dymatic.base.detail

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.*
import com.daijie.ksyllabusapp.ui.dymatic.base.detail.SchoolDymaticCommentContract.Companion.MESSAGE_COMMIT_ERROR
import com.daijie.ksyllabusapp.ui.dymatic.base.detail.SchoolDymaticCommentContract.Companion.MESSAGE_COMMIT_SUCCESS
import com.daijie.ksyllabusapp.ui.dymatic.base.detail.SchoolDymaticCommentContract.Companion.MESSAGE_DELETE_COMMIT_FAIL
import com.daijie.ksyllabusapp.ui.dymatic.base.detail.SchoolDymaticCommentContract.Companion.MESSAGE_DELETE_COMMIT_SUCCESS
import com.daijie.ksyllabusapp.ext.post
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.schooldymatic.SchoolDymaticDataRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository

/**
 * Created by daidaijie on 17-10-25.
 */
abstract class SchoolDymaticCommentPresenter(
        private val schoolDymaticCommentView: SchoolDymaticCommentContract.View,
        protected val userDataRepository: UserDataRepository,
        protected val schoolDymaticDataRepository: SchoolDymaticDataRepository,
        protected val subscriptioner: Subscriptioner
) : SchoolDymaticCommentContract.Presenter, ISubscriptioner by subscriptioner {

    override fun loadComments(schoolDymaticData: SchoolDymaticData) {
        userDataRepository.currentUser.post(schoolDymaticCommentView) { user ->
            schoolDymaticCommentView.startLoading()
            schoolDymaticDataRepository.getComments(schoolDymaticData.schoolNotice.id)
                    .to_ui()
                    .map {
                        it.map {
                            SchoolDymaticCommentData(it, it.uid == user.id)
                        }
                    }
                    .subscribe({
                        if (it.isNotEmpty()) {
                            schoolDymaticCommentView.showData(it)
                            schoolDymaticCommentView.showSuccess(OnePageLoadDataStatus.MESSAGE_SUCCESS_LOAD)
                        } else {
                            schoolDymaticCommentView.showSuccess(OnePageLoadDataStatus.MESSAGE_EMPTY_LOAD)
                        }
                        schoolDymaticCommentView.endLoading()
                    }, {
                        if (schoolDymaticDataRepository.isCommentsDataSourceEmpty(it)) {
                            schoolDymaticCommentView.showSuccess(OnePageLoadDataStatus.MESSAGE_EMPTY_LOAD)
                        } else {
                            schoolDymaticCommentView.showError(OnePageLoadDataStatus.MESSAGE_ERROR_LOAD)
                        }
                        schoolDymaticCommentView.endLoading()
                    })
                    .let {
                        add(it)
                    }
        }
    }

    override fun sendComment(schoolDymaticData: SchoolDymaticData, comment: String, isReSend: Boolean,
                             onSuccessCallback: (() -> Unit)?) {
        userDataRepository.currentUser.post(schoolDymaticCommentView) { user ->
            schoolDymaticCommentView.showLoadingDialog("正在发送评论")
            schoolDymaticDataRepository.sendComment(user, schoolDymaticData.schoolNotice.id, comment)
                    .to_ui()
                    .subscribe({
                        onSuccessCallback?.invoke()
                        schoolDymaticCommentView.hideLoadingDialog()
                        schoolDymaticCommentView.showSuccess(MESSAGE_COMMIT_SUCCESS)
                    }, {
                        schoolDymaticCommentView.hideLoadingDialog()
                        schoolDymaticCommentView.showError(MESSAGE_COMMIT_ERROR)
                        if (!isReSend) {
                            schoolDymaticCommentView.addFailComment(
                                    SchoolDymaticCommentData(
                                            SchoolDymaticComment(comment, "", -1, -1, -1,
                                                    SchoolDymaticCommentUser(
                                                            user.account, user.nickname, user.image
                                                    )),
                                            true, isCommentSuccess = false
                                    )
                            )
                        }
                    })
                    .let {
                        add(it)
                    }
        }
    }

    override fun deleteComments(commentData: SchoolDymaticCommentData, onSuccessCallback: (() -> Unit)?) {
        userDataRepository.currentUser.post(schoolDymaticCommentView) { user ->
            schoolDymaticCommentView.showLoadingDialog("正在删除评论")
            schoolDymaticDataRepository.deleteComment(user, commentData.schoolDymaticComment.id)
                    .to_ui()
                    .subscribe({
                        onSuccessCallback?.invoke()
                        schoolDymaticCommentView.hideLoadingDialog()
                        schoolDymaticCommentView.showSuccess(MESSAGE_DELETE_COMMIT_SUCCESS)
                    }, {
                        schoolDymaticCommentView.hideLoadingDialog()
                        schoolDymaticCommentView.showError(MESSAGE_DELETE_COMMIT_FAIL)
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