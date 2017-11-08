package com.daijie.ksyllabusapp.ext

import android.app.Activity
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import com.daijie.ksyllabusapp.widgets.DividerItemDecoration

/**
 * Created by daidaijie on 17-9-29.
 */
@Suppress("DEPRECATION")
val Activity.deviceWidth
    get() = windowManager.defaultDisplay.width

@Suppress("DEPRECATION")
val Activity.deviceHeight
    get() = windowManager.defaultDisplay.height


fun Activity.newDivLineItemDecoration(@DrawableRes drawable: Int, height: Int = 1): RecyclerView.ItemDecoration {
    val decLineDrawable = getDrawableById(drawable)
    val itemDecoration = DividerItemDecoration(
            this, DividerItemDecoration.VERTICAL_LIST, decLineDrawable)
    itemDecoration.setHeight(height)
    return itemDecoration
}

fun Activity.hideInput() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}