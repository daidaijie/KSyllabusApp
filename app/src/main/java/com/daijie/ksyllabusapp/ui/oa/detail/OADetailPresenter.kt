package com.daijie.ksyllabusapp.ui.oa.detail

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.OaInfo
import com.daijie.ksyllabusapp.data.OnePageLoadDataStatus
import com.daijie.ksyllabusapp.data.content
import com.daijie.ksyllabusapp.ext.from_computation
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.oafile.OaFileDataRepository
import com.orhanobut.logger.Logger
import rx.Observable
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-21.
 */
class OADetailPresenter @Inject constructor(
        private val oaDetailView: OADetailContract.View,
        private val oaInfo: OaInfo,
        private val subscriptioner: Subscriptioner,
        private val oaFileDataRepository: OaFileDataRepository
) : OADetailContract.Presenter, ISubscriptioner by subscriptioner {

    override fun loadOaFiles() {
        if (oaInfo.accessoryCount == 0) {
            oaDetailView.showSuccess(OnePageLoadDataStatus.MESSAGE_EMPTY_LOAD)
        } else {
            oaDetailView.startLoading()
            oaFileDataRepository.getOaFiles(oaInfo.id)
                    .to_ui()
                    .subscribe({
                        if (it.isNotEmpty()) {
                            oaDetailView.showFiles(it)
                            oaDetailView.showSuccess(OnePageLoadDataStatus.MESSAGE_SUCCESS_LOAD)
                        } else {
                            oaDetailView.showSuccess(OnePageLoadDataStatus.MESSAGE_EMPTY_LOAD)
                        }
                    }, {
                        oaDetailView.showError(OnePageLoadDataStatus.MESSAGE_ERROR_LOAD)
                    })
                    .let {
                        add(it)
                    }
        }
    }

    override fun subscribe() {
        Observable.fromCallable {
            oaInfo.content
        }.from_computation()
                .to_ui()
                .subscribe({
                    oaDetailView.showContent(it)
                }, {
                    Logger.e(it.message)
                })
                .let {
                    add(it)
                }
    }

    override fun unsubscribe() {
        dispose()
    }
}