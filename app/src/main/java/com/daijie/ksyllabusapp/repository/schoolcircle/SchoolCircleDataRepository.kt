package com.daijie.ksyllabusapp.repository.schoolcircle

import com.daijie.ksyllabusapp.di.qualifier.Remote
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-22.
 */
class SchoolCircleDataRepository @Inject constructor(
        @Remote private val schoolCircleRemoteDataSource: SchoolCircleDataSource
) :SchoolCircleDataSource by schoolCircleRemoteDataSource