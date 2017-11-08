package com.daijie.ksyllabusapp.base

/**
 * Created by daidaijie on 17-10-25.
 */
interface OnePageLoadDataView<T> : ShowStatusView {
    fun startLoading()
    fun showData(t: List<T>)
    fun endLoading()
}