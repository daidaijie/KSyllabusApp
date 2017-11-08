package com.daijie.ksyllabusapp.ui.dymatic.base.list

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.LoadDataView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.data.SchoolDymaticData

/**
 * Created by daidaijie on 2017/10/24.
 */
interface SchoolDymaticContract {

    interface Presenter : BasePresenter {
    }

    interface View : BaseView<Presenter>, ShowStatusView, LoadDataView<SchoolDymaticData> {

    }
}