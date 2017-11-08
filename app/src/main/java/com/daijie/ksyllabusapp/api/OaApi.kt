package com.daijie.ksyllabusapp.api

import com.daijie.ksyllabusapp.data.OaFile
import com.daijie.ksyllabusapp.data.OaInfo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable

/**
 * Created by daidaijie on 2017/10/18.
 */
interface OaApi {

    @FormUrlEncoded
    @POST("GetDoc")
    fun getOAInfo(@Field("token") token: String,
                  @Field("subcompany_id") subcompanyID: Int,
                  @Field("keyword") keyword: String,
                  @Field("row_start") start: Int,
                  @Field("row_end") end: Int
    ): Observable<List<OaInfo>>


    @FormUrlEncoded
    @POST("GetDOCAccessory")
    fun getOAFiles(@Field("token") token: String,
                   @Field("docid") docid: Long): Observable<List<OaFile>>

}