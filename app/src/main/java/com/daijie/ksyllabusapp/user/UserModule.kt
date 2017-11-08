package com.daijie.ksyllabusapp.user

import cn.edu.stu.syllabus.di.scope.UserScoped
import com.daijie.ksyllabusapp.api.LCLessonApi
import com.daijie.ksyllabusapp.api.LessonApi
import com.daijie.ksyllabusapp.api.LoginApi
import com.daijie.ksyllabusapp.di.qualifier.LCRetrofitQualifier
import com.daijie.ksyllabusapp.di.qualifier.Local
import com.daijie.ksyllabusapp.di.qualifier.Remote
import com.daijie.ksyllabusapp.di.qualifier.SchoolRetrofitQualifier
import com.daijie.ksyllabusapp.greendao.DaoSession
import com.daijie.ksyllabusapp.greendao.LessonRecordDao
import com.daijie.ksyllabusapp.greendao.LessonRuleRecordDao
import com.daijie.ksyllabusapp.greendao.ShareLessonRecordDao
import com.daijie.ksyllabusapp.repository.lesson.LessonDataSource
import com.daijie.ksyllabusapp.repository.lesson.local.LessonLocalDataSource
import com.daijie.ksyllabusapp.repository.lesson.remote.LessonRemoteDataSource
import com.daijie.ksyllabusapp.repository.lessonrule.LessonRuleDataSource
import com.daijie.ksyllabusapp.repository.lessonrule.local.LessonRuleLocalDataSource
import com.daijie.ksyllabusapp.repository.semester.SemesterDataSource
import com.daijie.ksyllabusapp.repository.semester.local.SemesterLocalDataSource
import com.daijie.ksyllabusapp.repository.user.UserDataSource
import com.daijie.ksyllabusapp.repository.user.local.UserLocalDataSource
import com.daijie.ksyllabusapp.repository.user.remote.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by daidaijie on 17-9-28.
 */
@Module
class UserModule {

    @Provides
    @UserScoped
    @Local
    fun provideUserLocalDataSource(): UserDataSource = UserLocalDataSource()

    @Provides
    @UserScoped
    fun provideLoginApi(@SchoolRetrofitQualifier retrofit: Retrofit)
            = retrofit.create(LoginApi::class.java)

    @Provides
    @UserScoped
    @Remote
    fun provideUserRemoteDataSource(loginApi: LoginApi): UserDataSource = UserRemoteDataSource(loginApi)

    @Provides
    @UserScoped
    @Local
    fun provideSemesterLocalDataSource(): SemesterDataSource = SemesterLocalDataSource()

    @Provides
    @UserScoped
    fun provideLessonRecordDao(daoSession: DaoSession): LessonRecordDao = daoSession.lessonRecordDao

    @Provides
    @UserScoped
    fun provideShareLessonRecordDao(daoSession: DaoSession): ShareLessonRecordDao = daoSession.shareLessonRecordDao

    @Provides
    @UserScoped
    @Local
    fun provideLessonLocalDataSource(lessonRecordDao: LessonRecordDao,
                                     shareLessonRecordDao: ShareLessonRecordDao): LessonDataSource
            = LessonLocalDataSource(lessonRecordDao, shareLessonRecordDao)

    @Provides
    @UserScoped
    fun provideLessonApi(@SchoolRetrofitQualifier retrofit: Retrofit)
            = retrofit.create(LessonApi::class.java)

    @Provides
    @UserScoped
    fun provideLCLessonApi(@LCRetrofitQualifier retrofit: Retrofit)
            = retrofit.create(LCLessonApi::class.java)


    @Provides
    @UserScoped
    @Remote
    fun provideLessonRemoteDataSource(lessonApi: LessonApi, lcLessonApi: LCLessonApi)
            : LessonDataSource = LessonRemoteDataSource(lessonApi, lcLessonApi)

    @Provides
    @UserScoped
    fun provideLessonRuleRecordDao(daoSession: DaoSession) = daoSession.lessonRuleRecordDao

    @Provides
    @UserScoped
    @Local
    fun provideLessonRuleLocalDataSource(lessonRuleRecordDao: LessonRuleRecordDao)
            : LessonRuleDataSource = LessonRuleLocalDataSource(lessonRuleRecordDao)
}