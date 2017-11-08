package com.daijie.ksyllabusapp.repository.schoolnotice

import com.daijie.ksyllabusapp.data.SchoolNotice
import rx.Observable

/**
 * Created by daidaijie on 2017/10/22.
 */
interface SchoolNoticeDataSource {

    fun getNotices(page: Int, limit: Int): Observable<List<SchoolNotice>>

    fun isSourceEmpty(errMsg: Throwable): Boolean
}