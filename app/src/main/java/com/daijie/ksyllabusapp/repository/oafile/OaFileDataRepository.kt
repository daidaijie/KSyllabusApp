package com.daijie.ksyllabusapp.repository.oafile

import com.daijie.ksyllabusapp.di.qualifier.Remote
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/22.
 */
class OaFileDataRepository @Inject constructor(@Remote private val oaFileRemoteDataSource: OaFileDataSource)
    : OaFileDataSource by oaFileRemoteDataSource