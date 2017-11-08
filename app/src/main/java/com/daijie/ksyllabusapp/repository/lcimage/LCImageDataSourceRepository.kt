package com.daijie.ksyllabusapp.repository.lcimage

import com.daijie.ksyllabusapp.di.qualifier.Remote
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-29.
 */
class LCImageDataSourceRepository @Inject constructor(
        @Remote private val lcImageRemoteDataSource: LCImageDataSource
) : LCImageDataSource by lcImageRemoteDataSource {
}