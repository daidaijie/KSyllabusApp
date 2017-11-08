package com.daijie.ksyllabusapp.user

import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.greendao.DaoMaster

/**
 * Created by daidaijie on 17-9-30.
 */
object GreenDaoDb {

    private val devOpenHelper = DaoMaster.DevOpenHelper(App.context, "syllabus-db")
    val daoMaster = DaoMaster(devOpenHelper.writableDatabase)
    val daoSession = daoMaster.newSession()

    fun close() {
        devOpenHelper.close()
    }
}