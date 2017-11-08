package com.daijie.ksyllabusapp.ui.selectlesson.collectiondetail

import com.daijie.ksyllabusapp.base.BasePresenter
import com.daijie.ksyllabusapp.base.BaseView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.base.ToLoginView
import com.daijie.ksyllabusapp.data.CollectionRecordChoice

/**
 * Created by daidaijie on 2017/10/15.
 */
interface CollectionDetailContract {
    interface Presenter : BasePresenter {
        fun loadCollectionDetail(code: String)
        fun handleCollectionChoice(records: List<CollectionRecordChoice>, weeks: String, isFullWeek: Boolean)
    }

    interface View : BaseView<Presenter>, ShowStatusView, ToLoginView {
        fun showCollectionRecords(records: List<CollectionRecordChoice>)
        fun toSyllabusAct(syllabusJson: String)
    }
}