package com.daijie.ksyllabusapp.ui.oa.list

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.LoadDataView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.data.OaInfo

/**
 * Created by daidaijie on 2017/10/18.
 */
interface OAListContract {

    interface Presenter : BasePresenter {
        fun loadOaInfos(start: Int, limit: Int)
    }

    interface View : BaseView<Presenter>, ShowStatusView, LoadDataView<OaInfo> {
    }
}