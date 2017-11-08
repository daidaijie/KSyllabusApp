package com.daijie.ksyllabusapp.repository.schoolnotice

import com.daijie.ksyllabusapp.di.qualifier.Remote
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/22.
 */
class SchoolNoticeDataRepository @Inject constructor(
        @Remote private val schoolNoticeRemoteDataSource: SchoolNoticeDataSource
) : SchoolNoticeDataSource by schoolNoticeRemoteDataSource