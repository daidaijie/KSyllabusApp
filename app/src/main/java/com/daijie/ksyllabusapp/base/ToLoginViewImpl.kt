package com.daijie.ksyllabusapp.base

import android.app.Activity
import com.daijie.ksyllabusapp.ui.login.LoginActivity

/**
 * Created by daidaijie on 17-10-17.
 */
class ToLoginViewImpl(private val activity: Activity) : ToLoginView {

    override fun toLoginAct() {
        activity.startActivity(LoginActivity.newIntent(activity, LoginActivity.TYPE_RELOGIN))
    }
}