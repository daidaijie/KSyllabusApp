package com.daijie.ksyllabusapp.repository.semester

import cn.edu.stu.syllabus.di.scope.UserScoped
import com.daijie.ksyllabusapp.di.qualifier.Local
import javax.inject.Inject

/**
 * Created by daidaijie on 17-9-29.
 */
@UserScoped
class SemesterRepository @Inject constructor(
        @Local private val semesterLocalDataSource: SemesterDataSource
) : SemesterDataSource by semesterLocalDataSource