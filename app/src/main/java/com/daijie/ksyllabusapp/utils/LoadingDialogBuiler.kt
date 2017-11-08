package com.daijie.ksyllabusapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.ext.getColorById

/**
 * Created by daidaijie on 17-9-28.
 */
object LoadingDialogBuiler {

    @JvmStatic
    @SuppressLint("InflateParams")
    fun build(
            context: Context,
            progressText: String,
            progressColor: Int = getColorById(R.color.colorAccent)
    ): AlertDialog {

        val loadingDialogLayout = LayoutInflater.from(context)
                .inflate(R.layout.dialog_loading, null, false) as RelativeLayout
        val mProgressTextView = loadingDialogLayout.findViewById<TextView>(R.id.progressTextView)
        mProgressTextView.text = progressText

        val loadingProgressBar = loadingDialogLayout
                .findViewById<ProgressBar>(R.id.loadingProgressBar)
        val dialog = AlertDialog.Builder(context)
                .setView(loadingDialogLayout)
                .create()
        loadingProgressBar.indeterminateDrawable.setColorFilter(
                progressColor,
                android.graphics.PorterDuff.Mode.SRC_IN)
        dialog.setCancelable(false)

        return dialog
    }
}