package com.daijie.ksyllabusapp.repository.schoolcircle.remote

import com.daijie.ksyllabusapp.api.SchoolDymaticApi
import com.daijie.ksyllabusapp.data.SchoolCircleRequest
import com.daijie.ksyllabusapp.data.SchoolNotice
import com.daijie.ksyllabusapp.ext.from_io
import com.daijie.ksyllabusapp.ext.takeData
import com.daijie.ksyllabusapp.repository.schoolcircle.SchoolCircleDataSource
import rx.Observable

/**
 * Created by daidaijie on 17-10-22.
 */
class SchoolCircleRemoteDataSource(private val schoolDymaticApi: SchoolDymaticApi)
    : SchoolCircleDataSource {

    override fun isSourceEmpty(errMsg: Throwable) = errMsg.message == "no resources yet"

    override fun getCircles(beforeId: Int, limit: Int): Observable<List<SchoolNotice>> {
        return schoolDymaticApi.getCircles(limit, beforeId)
                .from_io()
                .takeData()
                .map {
                    it.circleList
                }
    }

    override fun sendCircles(circleRequest: SchoolCircleRequest): Observable<Void> {
        return schoolDymaticApi.postCircle(circleRequest)
                .from_io()
                .takeData()
    }

}