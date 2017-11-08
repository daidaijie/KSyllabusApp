package com.daijie.ksyllabusapp.base

/**
 * Created by daidaijie on 2017/10/6.
 */
interface LoadingView {
    fun showLoadingDialog(msg: String? = null)
    fun hideLoadingDialog()
}