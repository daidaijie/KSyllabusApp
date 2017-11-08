package com.daijie.ksyllabusapp.ext

import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.repository.user.User
import com.daijie.ksyllabusapp.repository.user.get
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by daidaijie on 17-9-29.
 */

fun dp2px(dp: Float): Int {
    val scale = App.context.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun px2dp(px: Int): Float {
    val scale = App.context.resources.displayMetrics.density
    return px / scale + 0.5f
}

fun sp2px(sp: Int): Int {
    val scale = App.context.resources.displayMetrics.scaledDensity
    return (sp * scale + 0.5f).toInt()
}


fun getDrawableById(@DrawableRes id: Int) = ContextCompat.getDrawable(App.context, id)

fun getColorById(@ColorRes colorId: Int) = ContextCompat.getColor(App.context, colorId)

fun getStringById(@StringRes stringId: Int) = App.context.getString(stringId)

fun addFragmentToActivity(fragmentManager: FragmentManager,
                          fragment: Fragment, frameId: Int) {
    val transaction = fragmentManager.beginTransaction()
    transaction.add(frameId, fragment)
    transaction.commit()
}

fun char2time(c: Char): Int = when (c) {
    in '1'..'9' -> c.toInt() - 48
    '0' -> 10
    in 'A'..'C' -> c.toInt() - 'A'.toInt() + 11
    else -> throw RuntimeException("error time")
}

fun time2char(time: Int): Char = when (time) {
    in 1..9 -> (time + 48).toChar()
    10 -> '0'
    in 11..13 -> (time - 11 + 'A'.toInt()).toChar()
    else -> throw RuntimeException("error time")
}

fun createId() = Date().time

inline fun <reified T> genericType() = object : TypeToken<T>() {}.type

val weeks = listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")

@Suppress("DEPRECATION")
var clipboard: String
    get() {
        val cm = App.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return cm.text.toString()
    }
    set(value) {
        val cm = App.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.text = value
    }


fun User?.post(showStatusView: ShowStatusView, onSuccess: ((user: User) -> Unit)? = null) {
    this.get(onSuccess) {
        showStatusView.showError("用户未登录")
    }
}