package com.daijie.ksyllabusapp.repository.schooldymatic

import com.daijie.ksyllabusapp.di.qualifier.Remote
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-24.
 */
class SchoolDymaticDataRepository @Inject constructor(
        @Remote private val schoolDymaticRemoteDataSource: SchoolDymaticDataSource
) : SchoolDymaticDataSource by schoolDymaticRemoteDataSource