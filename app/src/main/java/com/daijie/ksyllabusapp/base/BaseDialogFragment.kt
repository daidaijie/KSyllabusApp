package com.daijie.ksyllabusapp.base

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View

/**
 * Created by daidaijie on 2017/10/3.
 */
abstract class BaseDialogFragment : DialogFragment() {

    protected abstract val contentViewId: Int

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(contentViewId, null, false)
        return initDialog(view, savedInstanceState)
    }

    abstract fun initDialog(view: View, savedInstanceState: Bundle?): Dialog
}