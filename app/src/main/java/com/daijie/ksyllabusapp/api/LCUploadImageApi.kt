package com.daijie.ksyllabusapp.api

import com.daijie.ksyllabusapp.data.LCFile
import com.daijie.ksyllabusapp.utils.LCRetrofit
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable

/**
 * Created by daidaijie on 17-10-29.
 */
interface LCUploadImageApi {

    @Headers("X-LC-Id: ${LCRetrofit.LC_ID}", "X-LC-Key: ${LCRetrofit.LC_KEY}")
    @POST("files/{filename}")
    fun uploadImage(@Body requestBody: RequestBody, @Path("filename") fileName: String): Observable<LCFile>

}