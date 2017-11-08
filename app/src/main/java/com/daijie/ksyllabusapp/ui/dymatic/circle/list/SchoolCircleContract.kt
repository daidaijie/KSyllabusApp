package com.daijie.ksyllabusapp.ui.dymatic.circle.list

import com.daijie.ksyllabusapp.ui.dymatic.base.list.SchoolDymaticContract

/**
 * Created by daidaijie on 17-10-22.
 */
interface SchoolCircleContract {

    interface Presenter : SchoolDymaticContract.Presenter {
        fun loadDatas(beforeId: Int, limit: Int, page: Int)
    }

    interface View : SchoolDymaticContract.View
}