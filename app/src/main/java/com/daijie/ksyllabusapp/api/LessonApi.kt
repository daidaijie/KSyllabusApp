package com.daijie.ksyllabusapp.api

import com.daijie.ksyllabusapp.data.*
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import retrofit2.http.*
import rx.Observable

/**
 * Created by daidaijie on 17-9-29.
 */
interface LessonApi {

    @FormUrlEncoded
    @POST("credit/api/v2.1/sync_syllabus")
    fun getLesson(@Field("username") username: String, @Field("password") password: String,
                  @Field("years") years: String, @Field("semester") semester: Int)
            : Observable<JsonResponse<JsonList<Lesson>>>


    @FormUrlEncoded
    @POST("credit/api/v2.1/custom_course")
    fun addLesson(@Field("username") username: String, @Field("token") token: String,
                  @Field("years") years: String, @Field("semester") semester: Int,
                  @Field("courses") courses: String): Observable<JsonResponse<Unit>>

    @DELETE("credit/api/v2.1/custom_course")
    fun deleteLesson(@Header("username") username: String, @Header("token") token: String,
                     @Header("years") years: String, @Header("semester") semester: Int,
                     @Header("id") id: String): Observable<JsonResponse<LessonStatusResponse>>


    @FormUrlEncoded
    @PUT("credit/api/v2.1/custom_course")
    fun updateLesson(@Field("username") username: String, @Field("token") token: String,
                     @Field("years") years: String, @Field("semester") semester: Int,
                     @Field("courses") courses: String): Observable<JsonResponse<LessonStatusResponse>>


    @GET("interaction/api/v2.1/collector")
    fun getCollectionList(@Header("username") username: String,
                          @Header("token") token: String): Observable<JsonResponse<CollectionInfoList>>

    @GET("interaction/api/v2.1/syllabus_collection")
    fun getCollectionDetail(@Header("username") username: String,
                            @Header("token") token: String,
                            @Header("collectionID") collectionId: String): Observable<JsonResponse<CollectionRecordList>>

    @FormUrlEncoded
    @POST("interaction/api/v2.1/collector")
    fun addCollection(@Field("username") username: String,
                      @Field("token") token: String,
                      @Field("start_year") start_year: Int,
                      @Field("season") season: Int): Observable<JsonResponse<CollectionInfo>>

    @FormUrlEncoded
    @POST("interaction/api/v2.1/syllabus_collection")
    fun sendSyllabusCollection(@Field("username") username: String,
                               @Field("token") token: String,
                               @Field("start_year") start_year: Int,
                               @Field("season") season: Int,
                               @Field("collection_id") collection_id: String,
                               @Field("syllabus") syllabus: String): Observable<JsonResponse<SendSyllabusResponse>>
}