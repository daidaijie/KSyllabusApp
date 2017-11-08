package com.daijie.ksyllabusapp.api

import com.daijie.ksyllabusapp.data.LCObject
import com.daijie.ksyllabusapp.data.ShareLessonData
import com.daijie.ksyllabusapp.utils.LCRetrofit
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable

/**
 * Created by daidaijie on 2017/10/6.
 */
interface LCLessonApi {

    @Headers("X-LC-Id: ${LCRetrofit.LC_ID}", "X-LC-Key: ${LCRetrofit.LC_KEY}", "Content-Type: application/json")
    @POST("classes/ShareLesson")
    fun shareLesson(@Body requestBody: RequestBody): Observable<LCObject>

    @Headers("X-LC-Id: ${LCRetrofit.LC_ID}", "X-LC-Key: ${LCRetrofit.LC_KEY}")
    @GET("classes/ShareLesson/{objectId}")
    fun getShareLessons(@Path("objectId") objectId: String): Observable<ShareLessonData>


    @Headers("X-LC-Id: ${LCRetrofit.LC_ID}", "X-LC-Key: ${LCRetrofit.LC_KEY}")
    @DELETE("classes/ShareLesson/{objectId}")
    fun deleteShareLesson(@Path("objectId") objectId: String): Observable<Unit>

}