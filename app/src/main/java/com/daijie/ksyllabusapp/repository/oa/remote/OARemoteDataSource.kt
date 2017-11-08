package com.daijie.ksyllabusapp.repository.oa.remote

import com.daijie.ksyllabusapp.api.OaApi
import com.daijie.ksyllabusapp.data.OaInfo
import com.daijie.ksyllabusapp.data.OaSearchInfo
import com.daijie.ksyllabusapp.ext.from_io
import com.daijie.ksyllabusapp.repository.oa.OADataSource
import rx.Observable

/**
 * Created by daidaijie on 2017/10/18.
 */
class OARemoteDataSource(val oaApi: OaApi) : OADataSource {

    override fun isSourceEmpty(errMsg: Throwable): Boolean {
        val errMsgClassName = errMsg.javaClass.simpleName
        return errMsgClassName == "EOFException" || errMsgClassName == "MalformedJsonException"
    }

    override fun getOaInfoEntries(oaSearchInfo: OaSearchInfo, start: Int, limit: Int): Observable<List<OaInfo>> {
        return oaApi.getOAInfo("undefined", oaSearchInfo.subCompanyId, oaSearchInfo.keyword
                , start, start + limit)
                .from_io()
    }
}