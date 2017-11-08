package com.daijie.ksyllabusapp.repository.banner.remote

import com.daijie.ksyllabusapp.api.BannerApi
import com.daijie.ksyllabusapp.data.BannerList
import com.daijie.ksyllabusapp.ext.from_io
import com.daijie.ksyllabusapp.ext.takeData
import com.daijie.ksyllabusapp.repository.banner.BannerDataSource
import rx.Observable

/**
 * Created by daidaijie on 2017/10/18.
 */
class BannerRemoteDataSource(val bannerApi: BannerApi) : BannerDataSource {

    override fun getBannerInfo() = bannerApi.getBanner()
            .from_io()
            .takeData()
            .map {
                it.bannerList
            }

    override fun saveBannerInfo(bannerList: BannerList) =
            throw RuntimeException("Can't save bannerList to remote")
}