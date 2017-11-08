package com.daijie.ksyllabusapp.base

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import com.daijie.ksyllabusapp.R

/**
 * Created by daidaijie on 17-10-25.
 */
abstract class BaseBottomDialog : DialogFragment() {

    companion object {
        const val DEFAULT_DIM = 0.2f
    }

    protected abstract val contentViewId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(getCancelOutside())
        val v = inflater.inflate(contentViewId, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        val window = dialog.window
        val params = window.attributes

        params.dimAmount = getDimAmount()
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        if (getHeight() > 0) {
            params.height = getHeight()
        } else {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        params.gravity = Gravity.BOTTOM

        window.attributes = params
    }

    abstract fun initView(view: View, savedInstanceState: Bundle?)

    protected open fun getCancelOutside() = true

    private fun getHeight() = -1

    private fun getDimAmount() = DEFAULT_DIM


}