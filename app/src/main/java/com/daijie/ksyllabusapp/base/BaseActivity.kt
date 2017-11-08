package com.daijie.ksyllabusapp.base

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.ext.deviceHeight
import com.daijie.ksyllabusapp.ext.deviceWidth
import com.daijie.ksyllabusapp.ext.getDrawableById
import com.daijie.ksyllabusapp.ext.statusBarHeight
import kotlin.properties.Delegates

/**
 * Created by daidaijie on 17-9-28.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected abstract val contentViewId: Int

    var mStatusBarHeight: Int by Delegates.notNull()

    var actHeight: Int by Delegates.notNull()
    var actWidth: Int by Delegates.notNull()

    protected open var titleToolbar: Toolbar? = null
    protected open var titleText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewId)
        initSize()
        setToolbar()
        initView(savedInstanceState)
    }

    private fun initSize() {
        mStatusBarHeight = statusBarHeight
        actHeight = deviceHeight
        actWidth = deviceWidth
    }

    abstract fun initView(savedInstanceState: Bundle?)

    private fun setToolbar() {
        titleToolbar?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val layoutParams = it.layoutParams
                layoutParams.height = layoutParams.height + mStatusBarHeight
            }
            it.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
            it.setNavigationOnClickListener {
                this@BaseActivity.finish()
            }
        }
    }

    protected fun setTitle(title: String) {
        titleText?.text = title
    }

}