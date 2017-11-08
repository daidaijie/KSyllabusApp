package com.daijie.ksyllabusapp.repository.banner

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.data.BannerList
import com.daijie.ksyllabusapp.di.qualifier.Local
import com.daijie.ksyllabusapp.di.qualifier.Remote
import rx.Observable
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/18.
 */
@ActivityScoped
class BannerDataRepository @Inject constructor(
        @Local private val bannerLocalDataSource: BannerDataSource,
        @Remote private val bannerRemoteDataSource: BannerDataSource
) : BannerDataSource {

    override fun getBannerInfo() =
            bannerRemoteDataSource.getBannerInfo()
                    .onErrorResumeNext {
                        bannerLocalDataSource.getBannerInfo()
                    }
                    .doOnNext {
                        bannerLocalDataSource.saveBannerInfo(it)
                    }

    override fun saveBannerInfo(bannerList: BannerList) =
            bannerLocalDataSource.saveBannerInfo(bannerList)

}