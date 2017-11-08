package com.daijie.ksyllabusapp.repository.schoolcircle

import com.daijie.ksyllabusapp.data.SchoolCircleRequest
import com.daijie.ksyllabusapp.data.SchoolNotice
import rx.Observable

/**
 * Created by daidaijie on 17-10-22.
 */
interface SchoolCircleDataSource {

    fun getCircles(beforeId: Int, limit: Int): Observable<List<SchoolNotice>>

    fun sendCircles(circleRequest: SchoolCircleRequest): Observable<Void>

    fun isSourceEmpty(errMsg: Throwable): Boolean
}