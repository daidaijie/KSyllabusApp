package com.daijie.ksyllabusapp.ui.dymatic.base.delete

import com.daijie.ksyllabusapp.data.SchoolDymaticData
import kotlin.properties.Delegates

/**
 * Created by daidaijie on 17-10-28.
 */
class SchoolDymaticDeleteViewProxy(private val SchoolDymaticDeleteIView: SchoolDymaticDeleteContract.IView)
    : SchoolDymaticDeleteContract.View {

    override var presenter: SchoolDymaticDeleteContract.Presenter by Delegates.notNull()

    fun handleDeleteDymatic(schoolDymaticData: SchoolDymaticData, position: Int) {
        presenter.deleteDymatic(schoolDymaticData, position)
    }

    override fun showSuccess(msg: String) {
        SchoolDymaticDeleteIView.showSuccess(msg)
    }

    override fun showError(msg: String) {
        SchoolDymaticDeleteIView.showError(msg)
    }

    override fun removeDymaticItem(position: Int) {
        SchoolDymaticDeleteIView.removeDymaticItem(position)
    }

}