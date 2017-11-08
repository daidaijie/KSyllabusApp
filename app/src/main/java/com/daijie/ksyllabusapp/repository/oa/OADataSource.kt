package com.daijie.ksyllabusapp.repository.oa

import com.daijie.ksyllabusapp.data.OaInfo
import com.daijie.ksyllabusapp.data.OaSearchInfo
import rx.Observable

/**
 * Created by daidaijie on 2017/10/18.
 */
interface OADataSource {

    fun getOaInfoEntries(oaSearchInfo: OaSearchInfo, start: Int, limit: Int): Observable<List<OaInfo>>

    fun isSourceEmpty(errMsg: Throwable): Boolean
}