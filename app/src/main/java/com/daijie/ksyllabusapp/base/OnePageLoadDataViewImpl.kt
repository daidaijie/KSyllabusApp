package com.daijie.ksyllabusapp.base

import android.content.Context
import com.daijie.ksyllabusapp.adapter.BaseQuickStateAdapter
import com.daijie.ksyllabusapp.data.OnePageLoadDataStatus
import com.daijie.ksyllabusapp.ext.toastE

/**
 * Created by daidaijie on 17-10-25.
 */
class OnePageLoadDataViewImpl<T>(
        private val context: Context,
        private val baseQuickStateAdapter: BaseQuickStateAdapter<T, *>)
    : OnePageLoadDataView<T> {

    override fun showSuccess(msg: String) {
        when (msg) {
            OnePageLoadDataStatus.MESSAGE_EMPTY_LOAD -> baseQuickStateAdapter.showEmpty()
        }
    }

    override fun startLoading() {
        baseQuickStateAdapter.showPre()
    }

    override fun showError(msg: String) {
        when (msg) {
            OnePageLoadDataStatus.MESSAGE_ERROR_LOAD -> baseQuickStateAdapter.showError()
            else -> context.toastE(msg)
        }
    }

    override fun showData(t: List<T>) {
        baseQuickStateAdapter.setNewData(t)
    }

    override fun endLoading() {
    }
}