package com.daijie.ksyllabusapp.ui.dymatic.base.detail

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.LoadingView
import com.daijie.ksyllabusapp.base.OnePageLoadDataView
import com.daijie.ksyllabusapp.data.SchoolDymaticCommentData
import com.daijie.ksyllabusapp.data.SchoolDymaticData

/**
 * Created by daidaijie on 17-10-25.
 */
interface SchoolDymaticCommentContract {
    companion object {
        const val MESSAGE_COMMIT_SUCCESS = "评论成功"
        const val MESSAGE_COMMIT_ERROR = "评论失败，但信息已保留至草稿"
        const val MESSAGE_DELETE_COMMIT_SUCCESS = "删除成功"
        const val MESSAGE_DELETE_COMMIT_FAIL = "删除失败"
    }

    interface Presenter : BasePresenter {
        fun loadComments(schoolDymaticData: SchoolDymaticData)
        fun sendComment(schoolDymaticData: SchoolDymaticData, comment: String, isReSend: Boolean = false, onSuccessCallback: (() -> Unit)? = null)
        fun deleteComments(commentData: SchoolDymaticCommentData, onSuccessCallback: (() -> Unit)? = null)
    }

    interface View : BaseView<Presenter>, OnePageLoadDataView<SchoolDymaticCommentData>, LoadingView {
        fun addFailComment(commentData: SchoolDymaticCommentData)
    }
}