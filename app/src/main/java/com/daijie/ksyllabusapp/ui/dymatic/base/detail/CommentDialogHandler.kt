package com.daijie.ksyllabusapp.ui.dymatic.base.detail

import android.support.v7.app.AlertDialog
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.adapter.SchoolDymaticCommentAdapter
import com.daijie.ksyllabusapp.data.SchoolDymaticCommentData
import com.daijie.ksyllabusapp.data.SchoolDymaticData
import com.daijie.ksyllabusapp.ext.clipboard
import com.daijie.ksyllabusapp.ext.toastS

/**
 * Created by daidaijie on 17-10-27.
 */
class CommentDialogHandler(private val schoolDymaticDetailActivity: SchoolDymaticDetailActivity,
                           private val schoolDymaticCommentPresenter: SchoolDymaticCommentContract.Presenter,
                           private val schoolDymaticCommentAdapter: SchoolDymaticCommentAdapter,
                           private val schoolDymaticData: SchoolDymaticData,
                           private val onReplyCallback: (SchoolDymaticCommentData) -> Unit) {

    companion object {
        @JvmStatic
        val DIALOG_OF_FAIL_COMMENTS_ITEMS = arrayOf("重新发送", "删除评论", "复制评论")
        @JvmStatic
        val DIALOG_OF_SUCCESS_COMMENTS_ITEMS_FOR_SUPER_USER = arrayOf("回复评论", "删除评论", "复制评论")
        @JvmStatic
        val DIALOG_OF_SUCCESS_COMMENTS_ITEMS_BY_ME = arrayOf("删除评论", "复制评论")
        @JvmStatic
        val DIALOG_OF_SUCCESS_COMMENTS_ITEMS_BY_OHTER = arrayOf("回复评论", "复制评论")
    }


    fun handleCommentDialog(commentData: SchoolDymaticCommentData, adapterPosition: Int, isLongClick: Boolean) {
        if (commentData.isCommentSuccess) {
            if (commentData.isMyComment) {
                showDialogOfSuccessCommentByMe(commentData, adapterPosition)
            } else {
                if (App.isSuperUser()) {
                    if (isLongClick) {
                        showDialogOfSuccessCommentForSuperUser(commentData, adapterPosition)
                    } else {
                        replyComment(commentData)
                    }
                } else {
                    if (isLongClick) {
                        showDialogOfSuccessCommentByOther(commentData, adapterPosition)
                    } else {
                        replyComment(commentData)
                    }
                }
            }
        } else {
            showDialogOfFailComment(commentData, adapterPosition)
        }
    }

    private fun showDialogOfSuccessCommentForSuperUser(commentData: SchoolDymaticCommentData, adapterPosition: Int) {
        val dialog = AlertDialog.Builder(schoolDymaticDetailActivity)
                .setTitle(commentData.schoolDymaticComment.user.account)
                .setItems(DIALOG_OF_SUCCESS_COMMENTS_ITEMS_FOR_SUPER_USER, { dialogInterface, position ->
                    when (position) {
                        0 -> {
                            replyComment(commentData)
                        }
                        1 -> {
                            removeSuccessComment(commentData, adapterPosition)
                        }
                        2 -> {
                            copyToBoard(commentData)
                        }
                    }
                })
                .create()
        dialog.show()
    }

    private fun showDialogOfSuccessCommentByMe(commentData: SchoolDymaticCommentData, adapterPosition: Int) {
        val dialog = AlertDialog.Builder(schoolDymaticDetailActivity)
                .setItems(DIALOG_OF_SUCCESS_COMMENTS_ITEMS_BY_ME, { dialogInterface, position ->
                    when (position) {
                        0 -> {
                            removeSuccessComment(commentData, adapterPosition)
                        }
                        1 -> {
                            copyToBoard(commentData)
                        }
                    }
                })
                .create()
        if (App.isSuperUser()) {
            // 这个其实没什么用,都是自己的账号
            dialog.setTitle(commentData.schoolDymaticComment.user.account)
        }
        dialog.show()
    }

    private fun showDialogOfSuccessCommentByOther(commentData: SchoolDymaticCommentData, adapterPosition: Int) {
        val dialog = AlertDialog.Builder(schoolDymaticDetailActivity)
                .setItems(DIALOG_OF_SUCCESS_COMMENTS_ITEMS_BY_OHTER, { dialogInterface, position ->
                    when (position) {
                        0 -> {
                            replyComment(commentData)
                        }
                        1 -> {
                            copyToBoard(commentData)
                        }
                    }
                })
                .create()
        dialog.show()
    }


    private fun showDialogOfFailComment(commentData: SchoolDymaticCommentData, adapterPosition: Int) {
        val dialog = AlertDialog.Builder(schoolDymaticDetailActivity)
                .setItems(DIALOG_OF_FAIL_COMMENTS_ITEMS, { _, position ->
                    when (position) {
                        0 -> {
                            reSendFailComment(commentData)
                        }
                        1 -> {
                            copyToBoard(commentData)
                        }
                        2 -> {
                            removeFailComment(commentData, adapterPosition)
                        }
                    }
                }).create()
        dialog.show()
    }

    private fun reSendFailComment(commentData: SchoolDymaticCommentData) {
        schoolDymaticCommentPresenter.sendComment(
                schoolDymaticData, commentData.schoolDymaticComment.comment, true
        ) {
            // 成功则删除这条失败的信息
            schoolDymaticCommentAdapter.removeFailComment(commentData)
        }
    }

    private fun replyComment(commentData: SchoolDymaticCommentData) {
        onReplyCallback(commentData)
    }

    private fun copyToBoard(commentData: SchoolDymaticCommentData) {
        clipboard = commentData.schoolDymaticComment.comment
        schoolDymaticDetailActivity.toastS("复制成功")
    }

    private fun removeSuccessComment(commentData: SchoolDymaticCommentData, position: Int) {
        schoolDymaticCommentPresenter.deleteComments(
                schoolDymaticCommentAdapter.data[position]
        ) {
            schoolDymaticCommentAdapter.removeWithState(position, true)
        }
    }

    private fun removeFailComment(commentData: SchoolDymaticCommentData, position: Int) {
        schoolDymaticCommentAdapter.removeWithState(position, setState = false)
        schoolDymaticCommentAdapter.removeFailComment(commentData)
    }

}