package com.daijie.ksyllabusapp.repository.lesson.remote

import com.daijie.ksyllabusapp.api.LCLessonApi
import com.daijie.ksyllabusapp.api.LessonApi
import com.daijie.ksyllabusapp.data.CollectionInfo
import com.daijie.ksyllabusapp.data.CollectionRecord
import com.daijie.ksyllabusapp.data.LCObject
import com.daijie.ksyllabusapp.data.ShareLessonData
import com.daijie.ksyllabusapp.ext.*
import com.daijie.ksyllabusapp.repository.lesson.DataEmptyError
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.repository.lesson.LessonDataSource
import com.daijie.ksyllabusapp.repository.semester.Semester
import com.daijie.ksyllabusapp.repository.user.User
import com.daijie.ksyllabusapp.utils.DefaultGson
import com.orhanobut.logger.Logger
import okhttp3.MediaType
import okhttp3.RequestBody
import rx.Observable

/**
 * Created by daidaijie on 17-9-28.
 */
class LessonRemoteDataSource(private val lessonApi: LessonApi, private val lcLessonApi: LCLessonApi)
    : LessonDataSource {

    override fun addLesson(user: User, semester: Semester, lessons: List<Lesson>): Observable<Unit> {
        return lessonApi.addLesson(user.account, user.token, semester.toYears(), semester.season,
                DefaultGson.gson.toJson(lessons))
                .from_io()
                .takeData()
    }

    override fun saveLesson(user: User, semester: Semester, lessons: List<Lesson>) =
            throw RuntimeException("not implement")

    override fun getLessons(user: User, semester: Semester, refresh: Boolean): Observable<List<Lesson>> {
        if (!refresh) {
            throw RuntimeException("can't unRefresh from remote")
        }
        return lessonApi.getLesson(user.account, user.password, semester.toYears(), semester.season)
                .from_io()
                .takeData()
                .takeList()
    }

    override fun deleteLesson(user: User, semester: Semester, lessons: Lesson): Observable<Boolean> {
        return lessonApi.deleteLesson(user.account, user.token,
                semester.toYears(), semester.season, lessons.id)
                .from_io()
                .takeData()
                .map {
                    true
                }
    }

    override fun updateLesson(user: User, semester: Semester, lessons: List<Lesson>): Observable<Boolean> {
        return lessonApi.updateLesson(user.account, user.token,
                semester.toYears(), semester.season, DefaultGson.gson.toJson(lessons))
                .from_io()
                .takeData()
                .map {
                    true
                }
    }


    override fun shareLesson(user: User, semester: Semester, lessons: List<Lesson>): Observable<LCObject> {
        lessons.forEach {
            it.isOthersLesson = true
        }

        val data = ShareLessonData(user.account, semester.startYear.toString(), semester.season.toString(),
                DefaultGson.gson.toJson(lessons))

        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                DefaultGson.gson.toJson(data))

        return lcLessonApi.shareLesson(body)
                .from_io()
    }

    override fun saveShareLessonRecord(user: User, semester: Semester, objectId: String, lessons: List<Lesson>) =
            throw RuntimeException("Can't not save shareLesson record to remote")


    override fun getShareLessonRecords(user: User, semester: Semester) =
            throw RuntimeException("not implement")

    override fun getShareLessonsById(objectId: String): Observable<ShareLessonData> {
        return lcLessonApi.getShareLessons(objectId)
                .from_io()
                .flatMap {
                    if (it.lessons.isNullOrBlank()) {
                        Observable.error(DataEmptyError())
                    } else {
                        Observable.just(it)
                    }
                }
    }

    override fun deleteShareLessonsById(objectId: String): Observable<Unit> {
        return lcLessonApi.deleteShareLesson(objectId)
                .from_io()
    }

    override fun getCollectionLessonList(user: User, semester: Semester): Observable<List<CollectionInfo>> {
        return lessonApi.getCollectionList(user.account, user.token)
                .from_io()
                .takeData()
                .doOnNext {
                    Logger.e(DefaultGson.gsonForPrint.toJson(it))
                }
                .map {
                    it.list.filter {
                        semester.startYear == it.startYear && semester.season == it.season
                    }.reversed()
                }
    }

    override fun applyCollectionLesson(user: User, semester: Semester): Observable<CollectionInfo> {
        return lessonApi.addCollection(user.account, user.token, semester.startYear, semester.season)
                .from_io()
                .takeData()
    }

    override fun sendSyllabus(user: User, semester: Semester, collectionId: String, lessons: List<Lesson>): Observable<Boolean> {
        return Observable.create<String> {
            try {
                it.onStart()
                val lessonTime = MutableList(7, {
                    MutableList(13, {
                        0
                    })
                })
                for (lesson in lessons) {
                    for (cs in lesson.classSchedule) {
                        for ((weekIndex, week) in cs.weeks.withIndex()) {
                            if (week == '1') {
                                for (time in cs.time) {
                                    val timeIndex = char2time(time) - 1
                                    val dateIndex = (if (cs.dayInWeek == 0) 7 else cs.dayInWeek) - 1
                                    lessonTime[dateIndex][timeIndex] = lessonTime[dateIndex][timeIndex] or (1 shl weekIndex)
                                }
                            }
                        }
                    }
                }
                Logger.e(DefaultGson.gsonForPrint.toJson(lessonTime))
                it.onNext(DefaultGson.gson.toJson(lessonTime))
                it.onCompleted()
            } catch (e: Exception) {
                it.onError(e)
            }
        }.from_computation()
                .to_io()
                .flatMap {
                    Logger.e(DefaultGson.gsonForPrint.toJson(it))
                    lessonApi.sendSyllabusCollection(user.account, user.token, semester.startYear, semester.season, collectionId, it)
                }
                .doOnNext {
                    Logger.e(DefaultGson.gsonForPrint.toJson(it))
                }
                .takeData()
                .map {
                    true
                }

    }

    override fun getCollectionDetail(user: User, semester: Semester, collectionId: String): Observable<List<CollectionRecord>> {
        return lessonApi.getCollectionDetail(user.account, user.token, collectionId)
                .from_io()
                .takeData()
                .map {
                    it.collections
                }
    }


}