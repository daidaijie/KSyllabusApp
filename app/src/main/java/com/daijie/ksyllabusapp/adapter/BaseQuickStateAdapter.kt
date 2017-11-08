package com.daijie.ksyllabusapp.adapter

import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.base.IStorable
import com.daijie.ksyllabusapp.utils.RecyclerViewState
import com.orhanobut.logger.Logger
import java.util.*

/**
 * Created by daidaijie on 17-10-21.
 */

abstract class BaseQuickStateAdapter<T, K : BaseViewHolder> : BaseQuickAdapter<T, K>, IStorable {

    companion object {
        private val EXTRA_DATA = "data"
        private val EXTRA_RECYCLER_VIEW_STATE = "recyclerViewState"
        private val EXTRA_CURRENT_PAGE = "currentPage"
    }

    @RecyclerViewState.RecyclerViewStateDef
    protected var recyclerViewState = RecyclerViewState.DATA_PRE

    var currentPage = 0

    protected var onErrorClickListener: (() -> Unit)? = null

    constructor(@LayoutRes layoutResId: Int, data: List<T>?) : super(layoutResId, data)

    constructor(data: List<T>?) : super(data)

    constructor(@LayoutRes layoutResId: Int) : super(layoutResId)

    @CallSuper
    override fun getStoreData(): Map<String, Any> {
        val storeMap = HashMap<String, Any>()
        storeMap.put(EXTRA_RECYCLER_VIEW_STATE, recyclerViewState)
        storeMap.put(EXTRA_DATA, data)
        storeMap.put(EXTRA_CURRENT_PAGE, currentPage)
        return storeMap
    }

    @Suppress("UNCHECKED_CAST")
    @CallSuper
    override fun restoreData(storeData: Map<String, Any>) {
        recyclerViewState = storeData[EXTRA_RECYCLER_VIEW_STATE] as Int
        currentPage = storeData[EXTRA_CURRENT_PAGE] as Int
        val datas = storeData[EXTRA_DATA] as List<T>?
        if (datas != null) {
            mData = datas
        } else {
            mData = mutableListOf<T>()
        }
    }

    fun restoreState() {
        when (recyclerViewState) {
            RecyclerViewState.DATA_PRE -> showPre()
            RecyclerViewState.DATA_EMPTY -> showEmpty()
            RecyclerViewState.DATA_ERROR -> showError()
            RecyclerViewState.LOAD_MORE_ERROR -> loadMoreFail()
            RecyclerViewState.LOAD_MORE_END -> loadMoreEnd()
        }
    }

    @CallSuper
    override fun setNewData(data: List<T>?) {
        super.setNewData(data)
        recyclerViewState = RecyclerViewState.DATA_SUCCESS

    }

    @CallSuper
    override fun addData(newData: Collection<T>) {
        super.addData(newData)
        recyclerViewState = RecyclerViewState.DATA_SUCCESS
    }

    open fun addDataWithState(position: Int, data: T, setState: Boolean = true) {
        super.addData(position, data)
        if (setState) {
            recyclerViewState = RecyclerViewState.DATA_SUCCESS
        }
    }

    open fun removeWithState(position: Int, setState: Boolean = true) {
        super.remove(position)
        if (setState) {
            if (data.isEmpty()) {
                showEmpty()
            }
        }
    }

    override fun addData(position: Int, newData: MutableCollection<out T>) {
        super.addData(position, newData)
        recyclerViewState = RecyclerViewState.DATA_SUCCESS
    }

    override fun remove(position: Int) {
        super.remove(position)
        if (data.isEmpty()) {
            showEmpty()
        }
    }


    @CallSuper
    override fun loadMoreEnd() {
        super.loadMoreEnd()
        recyclerViewState = RecyclerViewState.LOAD_MORE_END
    }

    @CallSuper
    override fun loadMoreFail() {
        super.loadMoreFail()
        recyclerViewState = RecyclerViewState.LOAD_MORE_ERROR
    }

    @CallSuper
    override fun loadMoreComplete() {
        super.loadMoreComplete()
    }

    @CallSuper
    open fun showError() {
        recyclerViewState = RecyclerViewState.DATA_ERROR
    }

    @CallSuper
    open fun showEmpty() {
        recyclerViewState = RecyclerViewState.DATA_EMPTY
    }

    @CallSuper
    open fun showPre() {
        recyclerViewState = RecyclerViewState.DATA_PRE
    }

    fun setOnErrorViewClickListener(listener: () -> Unit) {
        onErrorClickListener = listener
    }

    fun isPre() = recyclerViewState == RecyclerViewState.DATA_PRE
}
