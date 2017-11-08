package com.daijie.ksyllabusapp.ui.selectlesson.collectionlist

import com.daijie.ksyllabusapp.base.*
import com.daijie.ksyllabusapp.data.CollectionInfo

/**
 * Created by daidaijie on 2017/10/15.
 */
interface CollectionListContract {
    interface Presenter : BasePresenter {
        fun loadCollectionList()
        fun addCollection()
    }

    interface View : BaseView<Presenter>, ShowStatusView, LoadingView, ToLoginView {
        fun showCollectionList(collectionList: List<CollectionInfo>)
        fun addCollection(collectionInfo: CollectionInfo)
    }
}