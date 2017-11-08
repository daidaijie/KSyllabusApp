package com.daijie.ksyllabusapp.ui.dymatic.notice.list

import com.daijie.ksyllabusapp.ui.dymatic.base.list.SchoolDymaticContract

/**
 * Created by daidaijie on 2017/10/22.
 */
interface SchoolNoticeContract {

    interface Presenter : SchoolDymaticContract.Presenter {
        fun loadDatas(start: Int, limit: Int)
    }

    interface View : SchoolDymaticContract.View
}