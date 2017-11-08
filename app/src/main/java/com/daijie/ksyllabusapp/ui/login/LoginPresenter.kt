package com.daijie.ksyllabusapp.ui.login

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import com.daijie.ksyllabusapp.utils.DefaultGson
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * Created by daidaijie on 17-9-28.
 */
class LoginPresenter @Inject constructor(
        val loginView: LoginContract.View,
        val userDataRepository: UserDataRepository,
        val isFirst: Boolean,
        val subscriptioner: Subscriptioner
) : LoginContract.Presenter, ISubscriptioner by subscriptioner {

    override fun login(username: String, password: String) {
        loginView.showLoadingDialog("正在登录")
        userDataRepository.loginUser(username, password)
                .to_ui()
                .subscribe({
                    Logger.e(DefaultGson.gsonForPrint.toJson(it))
                    loginView.hideLoadingDialog()
                    loginView.showSuccess("登录成功")
                }, {
                    loginView.hideLoadingDialog()
                    loginView.showError(it.message ?: "登录失败")
                })
                .let {
                    add(it)
                }
    }

    override fun subscribe() {
        userDataRepository.currentUser
                ?.let { user ->
                    if (isFirst) {
                        loginView.showSuccess("登录成功")
                    } else {
                        loginView.setUsername(user.account)
                        loginView.setPassword(user.password)
                    }
                }
    }

    override fun unsubscribe() {
        subscriptioner.dispose()
    }
}