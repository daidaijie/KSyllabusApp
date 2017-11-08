package com.daijie.ksyllabusapp.repository.lessonrule.local

import com.daijie.ksyllabusapp.ext.fromJson
import com.daijie.ksyllabusapp.ext.from_io
import com.daijie.ksyllabusapp.greendao.LessonRuleRecordDao
import com.daijie.ksyllabusapp.repository.lessonrule.LessonRule
import com.daijie.ksyllabusapp.repository.lessonrule.LessonRuleDataSource
import com.daijie.ksyllabusapp.repository.lessonrule.LessonRuleRecord
import com.daijie.ksyllabusapp.repository.semester.Semester
import com.daijie.ksyllabusapp.repository.user.User
import com.daijie.ksyllabusapp.utils.DefaultGson
import rx.Observable

/**
 * Created by daidaijie on 17-10-17.
 */
class LessonRuleLocalDataSource(val lessonRuleRecordDao: LessonRuleRecordDao) : LessonRuleDataSource {

    override fun getLessonRules(user: User, semester: Semester): Observable<LessonRule> {
        return Observable.create<LessonRule> {
            try {
                it.onStart()
                val qb = lessonRuleRecordDao.queryBuilder()
                        .where(LessonRuleRecordDao.Properties.Account.eq(user.account),
                                LessonRuleRecordDao.Properties.SemesterYear.eq(semester.startYear),
                                LessonRuleRecordDao.Properties.SemesterSeason.eq(semester.season))
                val qlist = qb.build().list()
                if (qlist.isNotEmpty()) {
                    val lessonRule = DefaultGson.gson.fromJson<LessonRule>(qlist[0].lessonRecordsJson)
                    it.onNext(lessonRule)
                } else {
                    it.onNext(LessonRule())
                }
                it.onCompleted()
            } catch (e: Exception) {
                it.onError(e)
            }
        }.from_io()
    }

    override fun saveLessonRules(user: User, semester: Semester, lessonRule: LessonRule) {
        val qb = lessonRuleRecordDao.queryBuilder()
                .where(LessonRuleRecordDao.Properties.Account.eq(user.account),
                        LessonRuleRecordDao.Properties.SemesterYear.eq(semester.startYear),
                        LessonRuleRecordDao.Properties.SemesterSeason.eq(semester.season))
        val qlist = qb.build().list()
        qlist.forEach {
            lessonRuleRecordDao.delete(it)
        }

        val lessonRuleRecord = LessonRuleRecord()
        lessonRuleRecord.account = user.account
        lessonRuleRecord.semesterYear = semester.startYear
        lessonRuleRecord.semesterSeason = semester.season
        lessonRuleRecord.lessonRecordsJson = DefaultGson.gson.toJson(lessonRule)

        lessonRuleRecordDao.insert(lessonRuleRecord)
    }
}