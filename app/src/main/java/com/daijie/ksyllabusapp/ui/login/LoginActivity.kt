package com.daijie.ksyllabusapp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Html
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.base.LoadingViewImpl
import com.daijie.ksyllabusapp.ext.toastE
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.ext.year
import com.daijie.ksyllabusapp.ui.main.MainActivity
import com.daijie.ksyllabusapp.utils.TimeUtils
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject
import kotlin.properties.Delegates

class LoginActivity : BaseActivity(), LoginContract.View {

    companion object {
        const val EXTRA_IS_FIRST = "isFirst"
        const val EXTRA_ACCOUNT_EDITABLE = "accountEditable"

        const val TYPE_NORMAL = 0
        const val TYPE_RELOGIN = 1

        @JvmStatic
        fun newIntent(context: Context, type: Int) = when (type) {
            TYPE_NORMAL -> newIntent(context, isFirst = true, accountEditable = true)
            TYPE_RELOGIN -> newIntent(context, isFirst = false, accountEditable = false)
            else -> throw RuntimeException("Error type")
        }

        @JvmStatic
        fun newIntent(context: Context, isFirst: Boolean = false, accountEditable: Boolean = true)
                = Intent(context, LoginActivity::class.java)
                .apply {
                    putExtra(EXTRA_IS_FIRST, isFirst)
                    putExtra(EXTRA_ACCOUNT_EDITABLE, accountEditable)
                }
    }

    @Inject
    lateinit var loginPresenter: LoginPresenter

    override val contentViewId = R.layout.activity_login

    private var tipDialog: AlertDialog by Delegates.notNull()
    private var loadingDialog = LoadingViewImpl(this)

    private val username
        get() = usernameEditText.text.toString()

    private val password
        get() = passwordEditText.text.toString()

    private val isFirst: Boolean by lazy {
        intent.getBooleanExtra(EXTRA_IS_FIRST, true)
    }

    private val accountEditable: Boolean by lazy {
        intent.getBooleanExtra(EXTRA_ACCOUNT_EDITABLE, true)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initInject()
        initEditText()
        initCopyRight()
        initTip()
        initLoginButton()
        loginPresenter.subscribe()
    }

    private fun initEditText() {
        usernameEditText.isEnabled = accountEditable
    }

    override fun onDestroy() {
        loginPresenter.unsubscribe()
        super.onDestroy()
    }

    private fun initInject() {
        DaggerLoginComponent.builder()
                .userComponent(App.userComponent)
                .loginModule(LoginModule(this, isFirst))
                .build().inject(this)
    }

    private fun initCopyRight() {
        copyRightTextView.text = "汕头大学©${TimeUtils.today.year}"
    }

    private fun initTip() {
        tipDialog = AlertDialog.Builder(this)
                .setTitle("注意事项")
                .setMessage("我们不会在服务器保存密码，请放心使用。\n" +
                        "服务器可能会因为台风天或者其他原因被网络中心断电，非断电期间请相信" +
                        "我们服务器的稳定性。\n" +
                        "该版本有部分bug，程序员将尽快修复。")
                .setPositiveButton("确定", null)
                .create()

        @Suppress("DEPRECATION")
        tipTextView.text = Html.fromHtml("<u>注意事项</u>")
        tipTextView.setOnClickListener {
            tipDialog.show()
        }
    }

    private fun initLoginButton() {
        loginButton.setOnClickListener {
            when {
                username.isBlank() -> toastE("账号不能为空")
                password.isBlank() -> toastE("密码不能为空")
                else -> loginPresenter.login(username, password)
            }
        }
    }

    override fun showLoadingDialog(msg: String?) {
        loadingDialog.showLoadingDialog(msg)
    }

    override fun hideLoadingDialog() {
        loadingDialog.hideLoadingDialog()
    }

    override fun showSuccess(msg: String) {
        toastS(msg)
        if (isFirst) {
            startActivity(MainActivity.newIntent(this))
        }
        finish()
    }

    override fun showError(msg: String) {
        toastE(msg)
    }


    override fun setUsername(username: String) {
        usernameEditText.setText(username)
    }

    override fun setPassword(password: String) {
        passwordEditText.setText(password)
    }
}
