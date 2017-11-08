package com.daijie.ksyllabusapp.repository.schoolnotice.remote

import com.daijie.ksyllabusapp.api.SchoolDymaticApi
import com.daijie.ksyllabusapp.data.SchoolNotice
import com.daijie.ksyllabusapp.ext.from_io
import com.daijie.ksyllabusapp.ext.takeData
import com.daijie.ksyllabusapp.ext.takeList
import com.daijie.ksyllabusapp.repository.schoolnotice.SchoolNoticeDataSource
import rx.Observable

/**
 * Created by daidaijie on 2017/10/22.
 */
class SchoolNoticeRemoteDataSource(private val schoolDymaticApi: SchoolDymaticApi)
    : SchoolNoticeDataSource {

    override fun getNotices(page: Int, limit: Int): Observable<List<SchoolNotice>> {
        return schoolDymaticApi.getNotices(2, page, limit)
                .takeData()
                .takeList()
                .from_io()
    }

    override fun isSourceEmpty(errMsg: Throwable) = errMsg.message == "nothing found"
}