package com.daijie.ksyllabusapp.repository.oafile.remote

import com.daijie.ksyllabusapp.api.OaApi
import com.daijie.ksyllabusapp.ext.from_io
import com.daijie.ksyllabusapp.repository.oafile.OaFileDataSource

/**
 * Created by daidaijie on 2017/10/22.
 */
class OaFileRemoteDataSource(val oaApi: OaApi) : OaFileDataSource {
    override fun getOaFiles(docId: Long) = oaApi.getOAFiles("undefined", docId)
            .from_io()
}