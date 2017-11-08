package com.daijie.ksyllabusapp.base

/**
 * Created by daidaijie on 2017/10/22.
 */
interface LoadDataView<T> : ShowStatusView {
    fun startLoading(msg: String)
    fun endLoading(msg: String)
    fun appendDataList(dataList: List<T>, refresh: Boolean = false)
    fun isRefreshing(): Boolean
}