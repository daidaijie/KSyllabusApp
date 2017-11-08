package com.daijie.ksyllabusapp.repository.lesson

import com.daijie.ksyllabusapp.data.*
import com.daijie.ksyllabusapp.repository.semester.Semester
import com.daijie.ksyllabusapp.repository.user.User
import rx.Observable

/**
 * Created by daidaijie on 17-9-28.
 */
interface LessonDataSource {

    fun getLessons(user: User, semester: Semester, refresh: Boolean): Observable<List<Lesson>>

    fun saveLesson(user: User, semester: Semester, lessons: List<Lesson>)

    fun addLesson(user: User, semester: Semester, lessons: List<Lesson>): Observable<Unit>

    fun deleteLesson(user: User, semester: Semester, lessons: Lesson): Observable<Boolean>

    fun updateLesson(user: User, semester: Semester, lessons: List<Lesson>): Observable<Boolean>

    fun shareLesson(user: User, semester: Semester, lessons: List<Lesson>): Observable<LCObject>

    fun saveShareLessonRecord(user: User, semester: Semester, objectId: String, lessons: List<Lesson>)

    fun getShareLessonRecords(user: User, semester: Semester): Observable<List<ShareLessonBaseInfo>>

    fun getShareLessonsById(objectId: String): Observable<ShareLessonData>

    fun deleteShareLessonsById(objectId: String): Observable<Unit>

    fun getCollectionLessonList(user: User, semester: Semester): Observable<List<CollectionInfo>>

    fun applyCollectionLesson(user: User, semester: Semester): Observable<CollectionInfo>

    fun sendSyllabus(user: User, semester: Semester, collectionId: String, lessons: List<Lesson>): Observable<Boolean>

    fun getCollectionDetail(user: User, semester: Semester, collectionId: String): Observable<List<CollectionRecord>>
}