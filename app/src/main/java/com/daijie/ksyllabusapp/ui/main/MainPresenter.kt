package com.daijie.ksyllabusapp.ui.main

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.banner.BannerDataRepository
import com.daijie.ksyllabusapp.repository.semester.SemesterRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/18.
 */
class MainPresenter @Inject constructor(
        private val mainView: MainContract.View,
        private val userDataRepository: UserDataRepository,
        private val semesterRepository: SemesterRepository,
        private val bannerDataRepository: BannerDataRepository,
        private val subscriptioner: Subscriptioner
) : MainContract.Presenter, ISubscriptioner by subscriptioner {

    override fun subscribe() {
        loadBanner()
    }

    override fun loadBanner() {
        bannerDataRepository.getBannerInfo()
                .map {
                    it.banners
                }
                .to_ui()
                .subscribe({
                    mainView.showBanner(it)
                }, {})
                .let {
                    add(it)
                }
    }

    override fun unsubscribe() {
        dispose()
    }
}