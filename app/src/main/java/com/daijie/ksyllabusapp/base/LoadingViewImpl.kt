package com.daijie.ksyllabusapp.base

import android.app.Activity
import android.support.v7.app.AlertDialog
import com.daijie.ksyllabusapp.utils.LoadingDialogBuiler

/**
 * Created by daidaijie on 2017/10/15.
 */
class LoadingViewImpl(val activity: Activity) : LoadingView {

    private var loadingDialog: AlertDialog? = null

    override fun showLoadingDialog(msg: String?) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialogBuiler.build(activity, msg ?: "加载中")
        } else {
            loadingDialog?.setMessage(msg ?: "加载中")
        }
        loadingDialog?.show()
    }

    override fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }
}