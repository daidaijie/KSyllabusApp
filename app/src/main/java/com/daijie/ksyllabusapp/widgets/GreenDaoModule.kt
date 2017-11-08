package com.daijie.ksyllabusapp.widgets

import com.daijie.ksyllabusapp.user.GreenDaoDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by daidaijie on 17-9-30.
 */
@Module
class GreenDaoModule {

    @Provides
    @Singleton
    fun provideDaoSession() = GreenDaoDb.daoSession
}