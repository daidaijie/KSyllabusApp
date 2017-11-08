package com.daijie.ksyllabusapp

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import com.daijie.ksyllabusapp.user.UserComponent
import com.facebook.stetho.Stetho
import com.orhanobut.logger.Logger
import com.squareup.leakcanary.LeakCanary
import java.util.*
import kotlin.properties.Delegates

//
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                           O\  =  /O
//                        ____/`---'\____
//                      .'  \\|     |//  `.
//                     /  \\|||  :  |||//  \
//                    /  _||||| -:- |||||-  \
//                    |   | \\\  -  /// |   |
//                    | \_|  ''\---/''  |   |
//                    \  .-\__  `-`  ___/-. /
//                  ___`. .'  /--.--\  `. . __
//               ."" '<  `.___\_<|>_/___.'  >'"".
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//              \  \ `-.   \_ __\ /__ _/   .-` /  /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//         ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                      佛祖保佑       永无BUG

//           佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？
//


class App : Application() {

    companion object {
        @JvmStatic
        var context: Context by Delegates.notNull()

        @JvmStatic
        val TAG = "SyllabusApp"

        @JvmStatic
        var instance: App by Delegates.notNull()

        @JvmStatic
        var appComponent: AppComponent by Delegates.notNull()

        @JvmStatic
        var userComponent: UserComponent by Delegates.notNull()

        @JvmStatic
        fun isSuperUser() = userComponent.userDataRepository().currentUser?.level ?: -1 >= 2
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = this.applicationContext

        initLeakCanary()
        initStetho()
        initLogger()
        initInject()
        initVectorSupport()
        initTimeZone()
    }

    private fun initLeakCanary() {
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this)
        }
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initLogger() {
        Logger.init(TAG)
    }

    private fun initInject() {
        @Suppress("DEPRECATION")
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(context))
                .build()
        userComponent = appComponent.userComponent()
    }

    private fun initVectorSupport() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private fun initTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"))
    }
}

