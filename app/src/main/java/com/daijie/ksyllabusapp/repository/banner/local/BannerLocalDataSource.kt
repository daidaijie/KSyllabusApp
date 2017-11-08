package com.daijie.ksyllabusapp.repository.banner.local

import android.content.Context
import android.content.SharedPreferences
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.data.BannerList
import com.daijie.ksyllabusapp.ext.read
import com.daijie.ksyllabusapp.ext.write
import com.daijie.ksyllabusapp.repository.banner.BannerDataSource
import com.daijie.ksyllabusapp.repository.lesson.DataEmptyError
import rx.Observable

/**
 * Created by daidaijie on 2017/10/18.
 */
class BannerLocalDataSource : BannerDataSource {

    companion object {
        const val EXTRA_BANNER = "banner"
    }

    private val bannerPreferences: SharedPreferences by lazy {
        App.context.getSharedPreferences("Banner", Context.MODE_PRIVATE)
    }

    override fun getBannerInfo(): Observable<BannerList> {
        val bannerList = bannerPreferences.read<BannerList>(EXTRA_BANNER)
        if (bannerList != null) {
            return Observable.just(bannerList)
        } else {
            return Observable.error(DataEmptyError())
        }
    }

    override fun saveBannerInfo(bannerList: BannerList) {
        bannerPreferences.write(EXTRA_BANNER, bannerList)
    }


}