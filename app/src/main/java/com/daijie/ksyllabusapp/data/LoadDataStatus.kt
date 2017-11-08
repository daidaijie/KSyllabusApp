package com.daijie.ksyllabusapp.data

/**
 * Created by daidaijie on 2017/10/22.
 */
object LoadDataStatus {
    const val REQUEST_REFRESH = "刷新"
    const val REQUEST_LOAD = "加载"

    const val MESSAGE_ERROR_REFRESH = "刷新失败"
    const val MESSAGE_ERROR_LOAD = "加载失败"

    const val MESSAGE_SUCCESS_REFRESH = "刷新成功"
    const val MESSAGE_SUCCESS_LOAD = "加载成功"
    const val MESSAGE_EMPTY_LOAD = "加载完成"
    const val MESSAGE_EMPTY_REFRESH = "刷新完成"

    fun getSuccessMessage(start: Int) =
            if (start == 0)
                LoadDataStatus.MESSAGE_SUCCESS_REFRESH
            else
                LoadDataStatus.MESSAGE_SUCCESS_LOAD

    fun getEmptyMessage(start: Int) =
            if (start == 0)
                LoadDataStatus.MESSAGE_EMPTY_REFRESH
            else
                LoadDataStatus.MESSAGE_EMPTY_LOAD

    fun getErrorMessage(start: Int) =
            if (start == 0)
                LoadDataStatus.MESSAGE_ERROR_REFRESH
            else
                LoadDataStatus.MESSAGE_ERROR_LOAD


    fun getRequestMethod(start: Int) =
            if (start == 0)
                LoadDataStatus.REQUEST_REFRESH
            else
                LoadDataStatus.REQUEST_LOAD
}