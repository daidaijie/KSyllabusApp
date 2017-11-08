package com.daijie.ksyllabusapp.base

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import com.daijie.ksyllabusapp.adapter.BaseQuickStateAdapter
import com.daijie.ksyllabusapp.data.LoadDataStatus
import com.daijie.ksyllabusapp.ext.toastE
import com.daijie.ksyllabusapp.widgets.CustomLayoutManager

/**
 * Created by daidaijie on 2017/10/22.
 */
class LoadDataViewImpl<T>(
        private val context: Context,
        private val baseQuickStateAdapter: BaseQuickStateAdapter<T, *>,
        private val customLayoutManager: CustomLayoutManager,
        private val swipeRefreshLayout: SwipeRefreshLayout
) : LoadDataView<T>, ShowStatusView {

    private var isRefresh = false

    override fun startLoading(msg: String) {
        when (msg) {
            LoadDataStatus.REQUEST_REFRESH -> {
                isRefresh = true
                baseQuickStateAdapter.showPre()
                swipeRefreshLayout.isRefreshing = true
                customLayoutManager.isScrollEnabled = false
                baseQuickStateAdapter.setEnableLoadMore(false)
            }
            LoadDataStatus.REQUEST_LOAD -> swipeRefreshLayout.isEnabled = false
        }
    }

    override fun endLoading(msg: String) {
        when (msg) {
            LoadDataStatus.REQUEST_REFRESH -> {
                isRefresh = false;
                swipeRefreshLayout.isRefreshing = false
                customLayoutManager.isScrollEnabled = true
                baseQuickStateAdapter.setEnableLoadMore(true)
            }
            LoadDataStatus.REQUEST_LOAD -> swipeRefreshLayout.isEnabled = true
        }
    }

    override fun showSuccess(msg: String) {
        when (msg) {
            LoadDataStatus.MESSAGE_SUCCESS_LOAD -> baseQuickStateAdapter.loadMoreComplete()
            LoadDataStatus.MESSAGE_EMPTY_REFRESH -> baseQuickStateAdapter.showEmpty()
            LoadDataStatus.MESSAGE_EMPTY_LOAD -> baseQuickStateAdapter.loadMoreEnd()
        }
    }

    override fun showError(msg: String) {
        if (msg == LoadDataStatus.MESSAGE_ERROR_LOAD) {
            baseQuickStateAdapter.loadMoreFail()
        } else if (msg == LoadDataStatus.MESSAGE_ERROR_REFRESH) {
            if (baseQuickStateAdapter.data.isNotEmpty()) {
                context.toastE(msg)
            }
            baseQuickStateAdapter.showError()
        } else {
            context.toastE(msg)
        }
    }

    override fun appendDataList(dataList: List<T>, refresh: Boolean) {
        if (refresh) {
            baseQuickStateAdapter.currentPage = 1
            baseQuickStateAdapter.setNewData(dataList)
        } else {
            baseQuickStateAdapter.currentPage++
            baseQuickStateAdapter.addData(dataList)
        }
    }

    override fun isRefreshing() = isRefresh
}