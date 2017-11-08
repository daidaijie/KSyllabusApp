package com.daijie.ksyllabusapp.ext

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by daidaijie on 2017/10/20.
 */

fun EditText.showInput() {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun EditText.hideInput() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun EditText.moveCursorToEnd() {
    val info = text.toString()
    setSelection(info.length)
}

fun EditText.getEnterCount(): Int {
    val text = getText().toString()
    return text.count {
        it == '\n'
    }
}