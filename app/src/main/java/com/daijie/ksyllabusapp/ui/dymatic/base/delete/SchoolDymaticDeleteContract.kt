package com.daijie.ksyllabusapp.ui.dymatic.base.delete

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.data.SchoolDymaticData

/**
 * Created by daidaijie on 17-10-28.
 */
interface SchoolDymaticDeleteContract {

    companion object {
        const val MESSAGE_DELETE_SCHOOL_DYMATIC_SUCCESS = "删除动态成功"
        const val MESSAGE_DELETE_SCHOOL_DYMATIC_FAIL = "删除动态失败"
    }

    interface Presenter : BasePresenter {
        fun deleteDymatic(schoolDymaticData: SchoolDymaticData, position: Int)
    }

    interface View : BaseView<Presenter>, IView {
        var presenter: Presenter
    }

    interface IView : ShowStatusView {
        fun removeDymaticItem(position: Int)
    }
}