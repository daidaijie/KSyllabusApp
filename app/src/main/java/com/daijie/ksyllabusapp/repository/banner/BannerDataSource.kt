package com.daijie.ksyllabusapp.repository.banner

import com.daijie.ksyllabusapp.data.BannerList
import rx.Observable

/**
 * Created by daidaijie on 2017/10/18.
 */
interface BannerDataSource {

    fun getBannerInfo(): Observable<BannerList>
    fun saveBannerInfo(bannerList: BannerList)
}