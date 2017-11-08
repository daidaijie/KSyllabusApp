package com.daijie.ksyllabusapp.api

import com.daijie.ksyllabusapp.data.BannerInfo
import com.daijie.ksyllabusapp.data.JsonResponse
import retrofit2.http.GET
import rx.Observable

/**
 * Created by daidaijie on 2017/10/18.
 */
interface BannerApi {

    @GET("interaction/api/v2.1/banner")
    fun getBanner(): Observable<JsonResponse<BannerInfo>>
}