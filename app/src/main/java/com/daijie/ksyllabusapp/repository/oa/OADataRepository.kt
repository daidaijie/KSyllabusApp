package com.daijie.ksyllabusapp.repository.oa

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.data.OaSearchInfo
import com.daijie.ksyllabusapp.di.qualifier.Remote
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/18.
 */
@ActivityScoped
class OADataRepository @Inject constructor(
        @Remote private val oaRemoteDataSource: OADataSource
) : OADataSource by oaRemoteDataSource