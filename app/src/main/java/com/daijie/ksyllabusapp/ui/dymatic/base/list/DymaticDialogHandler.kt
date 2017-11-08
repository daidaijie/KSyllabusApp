package com.daijie.ksyllabusapp.ui.dymatic.base.list

import android.content.Context
import android.support.v7.app.AlertDialog
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.data.SchoolDymaticData

/**
 * Created by daidaijie on 17-10-28.
 */
class DymaticDialogHandler(private val context: Context,
                           private val onDymaticDialogSelectedListener: OnDymaticDialogSelectedListener) {

    companion object {
        const val ACTION_REPLY_DYMATIC = "回复动态"
        const val ACTION_DELETE_DYMATIC = "删除动态"
        const val ACTION_COPY_DYMATIC_TEXT = "复制文字"

        @JvmStatic
        val DIALOG_OF_DYMATIC_ITEMS_FOR_SUPER_USER_OR_ME = arrayOf("回复动态", "删除动态", "复制文字")
        @JvmStatic
        val DIALOG_OF_DYMATIC_ITEMS_BY_OTHER = arrayOf("回复动态", "复制文字")
    }

    fun handleDymaticDialog(schoolDymaticData: SchoolDymaticData, adapterPosition: Int) {
        val isSuperUser = App.isSuperUser()
        if (isSuperUser || schoolDymaticData.isMyPost) {
            showDymaticDialog(schoolDymaticData, adapterPosition, DIALOG_OF_DYMATIC_ITEMS_FOR_SUPER_USER_OR_ME, isSuperUser)
        } else {
            showDymaticDialog(schoolDymaticData, adapterPosition, DIALOG_OF_DYMATIC_ITEMS_BY_OTHER)
        }
    }

    private fun showDymaticDialog(schoolDymaticData: SchoolDymaticData, adapterPosition: Int, items: Array<String>, showTitle: Boolean = false) {
        val dialog = AlertDialog.Builder(context)
                .setItems(items, { dialogInterface, position ->
                    when (items[position]) {
                        ACTION_COPY_DYMATIC_TEXT -> onDymaticDialogSelectedListener.copyDymaticText(schoolDymaticData)
                        ACTION_REPLY_DYMATIC -> onDymaticDialogSelectedListener.replyDymatic(schoolDymaticData, adapterPosition)
                        ACTION_DELETE_DYMATIC -> showReadyDeleteDialog(schoolDymaticData, adapterPosition)
                    }
                })
                .create()
        if (showTitle) {
            dialog.setTitle(schoolDymaticData.schoolNotice.schoolDymaticUser.account)
        }
        dialog.show()
    }

    private fun showReadyDeleteDialog(schoolDymaticData: SchoolDymaticData, adapterPosition: Int) {
        val dialog = AlertDialog.Builder(context)
                .setTitle("警告")
                .setMessage("确定删除该动态？")
                .setPositiveButton("确定", { _, _ ->
                    onDymaticDialogSelectedListener.deleteDymatic(schoolDymaticData, adapterPosition)
                })
                .setNegativeButton("取消", null)
                .create()
        dialog.show()
    }

    interface OnDymaticDialogSelectedListener {
        fun replyDymatic(schoolDymaticData: SchoolDymaticData, adapterPosition: Int)
        fun copyDymaticText(schoolDymaticData: SchoolDymaticData)
        fun deleteDymatic(schoolDymaticData: SchoolDymaticData, adapterPosition: Int)
    }
}