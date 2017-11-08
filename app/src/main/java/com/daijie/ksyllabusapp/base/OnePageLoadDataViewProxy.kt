package com.daijie.ksyllabusapp.base

/**
 * Created by daidaijie on 17-10-26.
 */
interface OnePageLoadDataViewProxy<T> : OnePageLoadDataView<T> {

    val onePageLoadDataView: OnePageLoadDataView<T>

    override fun showSuccess(msg: String) {
        onePageLoadDataView.showSuccess(msg)
    }

    override fun startLoading() {
        onePageLoadDataView.startLoading()
    }

    override fun showError(msg: String) {
        onePageLoadDataView.showError(msg)
    }

    override fun showData(t: List<T>) {
        onePageLoadDataView.showData(t)
    }

    override fun endLoading() {
        onePageLoadDataView.endLoading()
    }
}