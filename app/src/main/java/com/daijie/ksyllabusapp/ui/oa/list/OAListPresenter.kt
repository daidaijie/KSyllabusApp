package com.daijie.ksyllabusapp.ui.oa.list

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.LoadDataStatus.getEmptyMessage
import com.daijie.ksyllabusapp.data.LoadDataStatus.getErrorMessage
import com.daijie.ksyllabusapp.data.LoadDataStatus.getRequestMethod
import com.daijie.ksyllabusapp.data.LoadDataStatus.getSuccessMessage
import com.daijie.ksyllabusapp.data.OaSearchInfo
import com.daijie.ksyllabusapp.data.isError
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.oa.OADataRepository
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/18.
 */
class OAListPresenter @Inject constructor(
        private val oaListView: OAListContract.View,
        private val oaSearchInfo: OaSearchInfo,
        private val oaDataRepository: OADataRepository,
        private val subscriptioner: Subscriptioner
) : OAListContract.Presenter, ISubscriptioner by subscriptioner {

    override fun subscribe() {
    }

    override fun loadOaInfos(start: Int, limit: Int) {
        oaListView.startLoading(getRequestMethod(start))
        oaDataRepository.getOaInfoEntries(oaSearchInfo, start, limit)
                .to_ui()
                .subscribe({
                    oaListView.showSuccess(getSuccessMessage(start))
                    val datas = it.filter {
                        !it.isError
                    }
                    oaListView.appendDataList(datas, start == 0)
                    oaListView.endLoading(getRequestMethod(start))
                }, {
                    if (oaDataRepository.isSourceEmpty(it)) {
                        oaListView.showSuccess(getEmptyMessage(start))
                    } else {
                        oaListView.showError(getErrorMessage(start))
                    }
                    oaListView.endLoading(getRequestMethod(start))
                })
                .let {
                    add(it)
                }
    }

    override fun unsubscribe() {
        dispose()
    }
}