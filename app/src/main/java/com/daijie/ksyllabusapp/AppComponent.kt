package com.daijie.ksyllabusapp

import com.daijie.ksyllabusapp.user.UserComponent
import com.daijie.ksyllabusapp.widgets.GreenDaoModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by daidaijie on 17-9-28.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, RetrofitModule::class, GreenDaoModule::class))
interface AppComponent : BaseAppProvider {
    fun inject(app: App)

    fun userComponent(): UserComponent
}