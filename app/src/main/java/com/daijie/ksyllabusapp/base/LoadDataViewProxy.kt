package com.daijie.ksyllabusapp.base

/**
 * Created by daidaijie on 17-10-26.
 */
interface LoadDataViewProxy<T> : LoadDataView<T> {

    val loadDataViewImpl: LoadDataViewImpl<T>

    override fun startLoading(msg: String) {
        loadDataViewImpl.startLoading(msg)
    }

    override fun endLoading(msg: String) {
        loadDataViewImpl.endLoading(msg)
    }

    override fun appendDataList(dataList: List<T>, refresh: Boolean) {
        loadDataViewImpl.appendDataList(dataList, refresh)
    }

    override fun isRefreshing() = loadDataViewImpl.isRefreshing()

    override fun showSuccess(msg: String) {
        loadDataViewImpl.showSuccess(msg)
    }

    override fun showError(msg: String) {
        loadDataViewImpl.showError(msg)
    }
}