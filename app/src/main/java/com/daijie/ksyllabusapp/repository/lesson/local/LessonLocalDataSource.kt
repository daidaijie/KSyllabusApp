package com.daijie.ksyllabusapp.repository.lesson.local

import com.daijie.ksyllabusapp.data.ShareLessonData
import com.daijie.ksyllabusapp.ext.fromJson
import com.daijie.ksyllabusapp.ext.from_io
import com.daijie.ksyllabusapp.greendao.LessonRecordDao
import com.daijie.ksyllabusapp.greendao.ShareLessonRecordDao
import com.daijie.ksyllabusapp.repository.SemesterLessons
import com.daijie.ksyllabusapp.repository.lesson.*
import com.daijie.ksyllabusapp.repository.semester.Semester
import com.daijie.ksyllabusapp.repository.user.User
import com.daijie.ksyllabusapp.utils.DefaultGson
import rx.Observable

/**
 * Created by daidaijie on 17-9-28.
 */
class LessonLocalDataSource(
        val lessonRecordDao: LessonRecordDao,
        val shareLessonRecordDao: ShareLessonRecordDao
) : LessonDataSource {

    var cacheLessonsRef: SemesterLessons? = null

    override fun addLesson(user: User, semester: Semester, lessons: List<Lesson>): Observable<Unit> {
        val qb = lessonRecordDao.queryBuilder().where(
                LessonRecordDao.Properties.Account.eq(user.account))
        qb.and(LessonRecordDao.Properties.SemesterYear.eq(semester.startYear),
                LessonRecordDao.Properties.SemesterYear.eq(semester.season))
        val qlist = qb.build().list()
        if (qlist.size > 0) {
            val lastLessonRecord = qlist[0]
            val lastLessons = DefaultGson.gson.fromJson<MutableList<Lesson>>(lastLessonRecord.lessonsJson)
            lastLessons.addAll(lessons)
            lastLessonRecord.lessonsJson = DefaultGson.gson.toJson(lastLessons)
            lessonRecordDao.update(lastLessonRecord)
            cacheLessonsRef = SemesterLessons(semester, lastLessons)
        } else {
            val newLessonRecord = LessonRecord()
            newLessonRecord.account = user.account
            newLessonRecord.semesterYear = semester.startYear
            newLessonRecord.semesterSeason = semester.season
            newLessonRecord.lessonsJson = DefaultGson.gson.toJson(lessons)
            lessonRecordDao.insert(newLessonRecord)
            cacheLessonsRef = SemesterLessons(semester, lessons)
        }
        return Observable.never()
    }

    override fun saveLesson(user: User, semester: Semester, lessons: List<Lesson>) {
        cacheLessonsRef = SemesterLessons(semester, lessons)

        val qb = lessonRecordDao.queryBuilder().where(
                LessonRecordDao.Properties.Account.eq(user.account))
        qb.and(LessonRecordDao.Properties.SemesterYear.eq(semester.startYear),
                LessonRecordDao.Properties.SemesterYear.eq(semester.season))
        qb.build().list().forEach {
            lessonRecordDao.deleteByKey(it.id)
        }

        val lessonRecord = LessonRecord()
        lessonRecord.account = user.account
        lessonRecord.semesterYear = semester.startYear
        lessonRecord.semesterSeason = semester.season
        lessonRecord.lessonsJson = DefaultGson.gson.toJson(lessons)
        lessonRecordDao.insert(lessonRecord)
    }

    override fun deleteLesson(user: User, semester: Semester, lessons: Lesson): Observable<Boolean> {
        return Observable.create<Boolean> {
            try {
                it.onStart()
                val qb = lessonRecordDao.queryBuilder().where(
                        LessonRecordDao.Properties.Account.eq(user.account))
                qb.and(LessonRecordDao.Properties.SemesterYear.eq(semester.startYear),
                        LessonRecordDao.Properties.SemesterYear.eq(semester.season))
                val qlist = qb.build().list()
                if (qlist.size > 0) {
                    val lastLessonRecord = qlist[0]
                    val lastLessons = DefaultGson.gson.fromJson<MutableList<Lesson>>(lastLessonRecord.lessonsJson)
                            .filter {
                                it.id != lessons.id
                            }
                    lastLessonRecord.lessonsJson = DefaultGson.gson.toJson(lastLessons)
                    lessonRecordDao.update(lastLessonRecord)
                    cacheLessonsRef = SemesterLessons(semester, lastLessons)
                }
                it.onNext(true)
                it.onCompleted()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    override fun updateLesson(user: User, semester: Semester, lessons: List<Lesson>): Observable<Boolean> {
        return Observable.create<Boolean> {
            try {
                it.onStart()
                val qb = lessonRecordDao.queryBuilder().where(
                        LessonRecordDao.Properties.Account.eq(user.account))
                qb.and(LessonRecordDao.Properties.SemesterYear.eq(semester.startYear),
                        LessonRecordDao.Properties.SemesterYear.eq(semester.season))
                val qlist = qb.build().list()
                if (qlist.size > 0) {
                    val lastLessonRecord = qlist[0]
                    val idSet = lessons.map {
                        it.id
                    }
                    val lastLessons = DefaultGson.gson.fromJson<MutableList<Lesson>>(lastLessonRecord.lessonsJson)
                            .filter {
                                it.id !in idSet
                            }.toMutableList()
                    lastLessons.addAll(lessons)
                    lastLessonRecord.lessonsJson = DefaultGson.gson.toJson(lastLessons)
                    lessonRecordDao.update(lastLessonRecord)
                    cacheLessonsRef = SemesterLessons(semester, lastLessons)
                }
                it.onNext(true)
                it.onCompleted()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }


    override fun getLessons(user: User, semester: Semester, refresh: Boolean): Observable<List<Lesson>> {
        if (refresh) {
            throw RuntimeException("Can't refresh from local")
        }

        val cacheLesson = cacheLessonsRef

        if (cacheLesson != null && cacheLesson.semester == semester) {
            return Observable.just(cacheLesson.lessons)
        } else {
            return Observable.create<List<Lesson>> {
                try {
                    it.onStart()
                    val qb = lessonRecordDao.queryBuilder().where(
                            LessonRecordDao.Properties.Account.eq(user.account))
                    qb.and(LessonRecordDao.Properties.SemesterYear.eq(semester.startYear),
                            LessonRecordDao.Properties.SemesterYear.eq(semester.season))
                    val list = qb.list()
                    val lessonRecord = if (list.size > 0) list[0] else null
                    if (lessonRecord != null) {
                        val lessons = DefaultGson.gson.fromJson<List<Lesson>>(lessonRecord.lessonsJson)
                        it.onNext(lessons)
                        it.onCompleted()
                    } else {
                        it.onError(DataEmptyError())
                    }
                } catch (e: Exception) {
                    it.onError(e)
                }
            }.from_io()
        }
    }

    override fun shareLesson(user: User, semester: Semester, lessons: List<Lesson>) =
            throw RuntimeException("Can't shareLesson to local")

    override fun saveShareLessonRecord(user: User, semester: Semester, objectId: String, lessons: List<Lesson>) {
        val shareLessonRecord = ShareLessonRecord()
        shareLessonRecord.objectId = objectId
        shareLessonRecord.account = user.account
        shareLessonRecord.semesterYear = semester.startYear
        shareLessonRecord.semesterSeason = semester.season
        shareLessonRecord.lessonsJson = DefaultGson.gson.toJson(lessons)

        shareLessonRecordDao.insert(shareLessonRecord)
    }

    override fun getShareLessonRecords(user: User, semester: Semester): Observable<List<ShareLessonBaseInfo>> {
        return Observable.create<List<ShareLessonBaseInfo>> {
            try {
                it.onStart()
                val qb = shareLessonRecordDao.queryBuilder().where(
                        ShareLessonRecordDao.Properties.Account.eq(user.account)
                )
                qb.and(ShareLessonRecordDao.Properties.SemesterYear.eq(semester.startYear),
                        ShareLessonRecordDao.Properties.SemesterYear.eq(semester.season))
                qb.orderDesc(ShareLessonRecordDao.Properties.Id)
                it.onNext(qb.list().map {
                    ShareLessonBaseInfo(it.objectId, DefaultGson.gson.fromJson<List<Lesson>>(it.lessonsJson))
                })
                it.onCompleted()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    override fun getShareLessonsById(objectId: String): Observable<ShareLessonData> =
            throw RuntimeException("Can't get shareLesson from local")

    override fun deleteShareLessonsById(objectId: String): Observable<Unit> {
        return Observable.create<Unit> {
            try {
                it.onStart()
                val qb = shareLessonRecordDao.queryBuilder().where(
                        ShareLessonRecordDao.Properties.ObjectId.eq(objectId)
                )
                qb.list().forEach {
                    shareLessonRecordDao.delete(it)
                }
                it.onNext(Unit)
                it.onCompleted()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    override fun getCollectionLessonList(user: User, semester: Semester) =
            throw RuntimeException("not implements")

    override fun applyCollectionLesson(user: User, semester: Semester) =
            throw RuntimeException("not implements")

    override fun sendSyllabus(user: User, semester: Semester, collectionId: String, lessons: List<Lesson>) =
            throw RuntimeException("Can't send syllabus to local")

    override fun getCollectionDetail(user: User, semester: Semester, collectionId: String) =
            throw RuntimeException("Can't get collection detail from local")

}