package com.daijie.ksyllabusapp.ui.main

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.data.Banner

/**
 * Created by daidaijie on 2017/10/18.
 */
interface MainContract {
    interface Presenter : BasePresenter {
        fun loadBanner()
    }

    interface View : BaseView<Presenter> {
        fun showBanner(banners: List<Banner>)
    }
}