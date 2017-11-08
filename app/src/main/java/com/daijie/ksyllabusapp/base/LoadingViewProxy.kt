package com.daijie.ksyllabusapp.base

/**
 * Created by daidaijie on 2017/10/26.
 */
interface LoadingViewProxy : LoadingView {

    val loadingView: LoadingView

    override fun showLoadingDialog(msg: String?) {
        loadingView.showLoadingDialog(msg)
    }

    override fun hideLoadingDialog() {
        loadingView.hideLoadingDialog()
    }
}