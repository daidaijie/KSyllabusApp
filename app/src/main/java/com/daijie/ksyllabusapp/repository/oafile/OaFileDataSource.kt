package com.daijie.ksyllabusapp.repository.oafile

import com.daijie.ksyllabusapp.data.OaFile
import rx.Observable

/**
 * Created by daidaijie on 2017/10/22.
 */
interface OaFileDataSource {
    fun getOaFiles(docId: Long): Observable<List<OaFile>>
}