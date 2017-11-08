package com.daijie.ksyllabusapp.repository.lesson

import cn.edu.stu.syllabus.di.scope.UserScoped
import com.daijie.ksyllabusapp.di.qualifier.Local
import com.daijie.ksyllabusapp.di.qualifier.Remote
import com.daijie.ksyllabusapp.repository.semester.Semester
import com.daijie.ksyllabusapp.repository.user.User
import rx.Observable
import javax.inject.Inject

/**
 * Created by daidaijie on 17-9-29.
 */
@UserScoped
class LessonDataRepository @Inject constructor(
        @Local private val lessonLocalDataSource: LessonDataSource,
        @Remote private val lessonRemoteDataSource: LessonDataSource)
    : LessonDataSource {

    override fun saveShareLessonRecord(user: User, semester: Semester, objectId: String, lessons: List<Lesson>) {
        lessonLocalDataSource.saveShareLessonRecord(user, semester, objectId, lessons)
    }

    override fun shareLesson(user: User, semester: Semester, lessons: List<Lesson>) =
            lessonRemoteDataSource.shareLesson(user, semester, lessons)
                    .doOnNext {
                        saveShareLessonRecord(user, semester, it.objectId, lessons)
                    }


    override fun addLesson(user: User, semester: Semester, lessons: List<Lesson>): Observable<Unit> {
        return lessonRemoteDataSource.addLesson(user, semester, lessons)
                .doOnNext {
                    lessonLocalDataSource.addLesson(user, semester, lessons).subscribe()
                }
    }

    override fun saveLesson(user: User, semester: Semester, lessons: List<Lesson>) {
        lessonLocalDataSource.saveLesson(user, semester, lessons)
    }

    override fun deleteLesson(user: User, semester: Semester, lessons: Lesson): Observable<Boolean> {
        return lessonRemoteDataSource.deleteLesson(user, semester, lessons)
                .onErrorResumeNext {
                    if (it.message == "resource not found") {
                        lessonLocalDataSource.deleteLesson(user, semester, lessons)
                    } else {
                        Observable.error(Throwable(it.message))
                    }
                }
                .flatMap {
                    lessonLocalDataSource.deleteLesson(user, semester, lessons)
                }
    }

    override fun updateLesson(user: User, semester: Semester, lessons: List<Lesson>): Observable<Boolean> {
        return lessonRemoteDataSource.updateLesson(user, semester, lessons)
                .flatMap {
                    lessonLocalDataSource.updateLesson(user, semester, lessons)
                }
    }

    override fun getLessons(user: User, semester: Semester, refresh: Boolean): Observable<List<Lesson>> {
        val refreshLessonObservable: Observable<List<Lesson>> by lazy {
            lessonRemoteDataSource
                    .getLessons(user, semester, true)
                    .doOnNext {
                        saveLesson(user, semester, it)
                    }
        }

        if (refresh) {
            return refreshLessonObservable
        } else {
            return lessonLocalDataSource.getLessons(user, semester, false)
                    .onErrorResumeNext {
                        if (it is DataEmptyError) {
                            refreshLessonObservable
                        } else {
                            Observable.error(it)
                        }
                    }
        }
    }

    override fun getShareLessonRecords(user: User, semester: Semester) =
            lessonLocalDataSource.getShareLessonRecords(user, semester)

    override fun getShareLessonsById(objectId: String) =
            lessonRemoteDataSource.getShareLessonsById(objectId)

    override fun deleteShareLessonsById(objectId: String): Observable<Unit> {
        return lessonRemoteDataSource.deleteShareLessonsById(objectId)
                .doOnCompleted {
                    lessonLocalDataSource.deleteShareLessonsById(objectId).subscribe()
                }
    }

    override fun getCollectionLessonList(user: User, semester: Semester) =
            lessonRemoteDataSource.getCollectionLessonList(user, semester)

    override fun applyCollectionLesson(user: User, semester: Semester) =
            lessonRemoteDataSource.applyCollectionLesson(user, semester)

    override fun sendSyllabus(user: User, semester: Semester, collectionId: String, lessons: List<Lesson>) =
            lessonRemoteDataSource.sendSyllabus(user, semester, collectionId, lessons)

    override fun getCollectionDetail(user: User, semester: Semester, collectionId: String) =
            lessonRemoteDataSource.getCollectionDetail(user, semester, collectionId)

}