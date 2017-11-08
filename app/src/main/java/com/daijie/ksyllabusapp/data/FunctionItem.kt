package com.daijie.ksyllabusapp.data

import android.support.annotation.DrawableRes
import com.daijie.ksyllabusapp.R

/**
 * Created by daidaijie on 2017/10/18.
 */
data class FunctionItem(
        val name: String,
        @DrawableRes val icon: Int
) {
    companion object {
        @JvmStatic
        val syllabusFunctionItem = FunctionItem("我的课表", R.drawable.ic_wifi)

        @JvmStatic
        val functionItems = listOf(
                FunctionItem("流量验证", R.drawable.ic_wifi),
                FunctionItem("流量验证", R.drawable.ic_wifi),
                FunctionItem("流量验证", R.drawable.ic_wifi),
                FunctionItem("流量验证", R.drawable.ic_wifi),
                FunctionItem("流量验证", R.drawable.ic_wifi),
                FunctionItem("流量验证", R.drawable.ic_wifi),
                FunctionItem("流量验证", R.drawable.ic_wifi),
                FunctionItem("流量验证", R.drawable.ic_wifi),
                FunctionItem("流量验证", R.drawable.ic_wifi)
        )
    }
}