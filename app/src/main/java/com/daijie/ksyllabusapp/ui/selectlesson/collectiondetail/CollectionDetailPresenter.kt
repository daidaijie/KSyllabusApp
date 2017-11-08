package com.daijie.ksyllabusapp.ui.selectlesson.collectiondetail

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.CollectionRecordChoice
import com.daijie.ksyllabusapp.ext.*
import com.daijie.ksyllabusapp.repository.lesson.LessonDataRepository
import com.daijie.ksyllabusapp.repository.semester.SemesterRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import com.daijie.ksyllabusapp.utils.DefaultGson
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/15.
 */
class CollectionDetailPresenter @Inject constructor(
        private val userDataRepository: UserDataRepository,
        private val semesterRepository: SemesterRepository,
        private val lessonDataRepository: LessonDataRepository,
        private val collectionDetailView: CollectionDetailContract.View,
        private val subscriptioner: Subscriptioner
) : CollectionDetailContract.Presenter, ISubscriptioner by subscriptioner {

    override fun subscribe() {
    }

    override fun loadCollectionDetail(code: String) {
        userDataRepository.currentUser.post(collectionDetailView) { user ->
            lessonDataRepository.getCollectionDetail(user, semesterRepository.currentSemester, code)
                    .to_ui()
                    .onErrorToLogin(collectionDetailView)
                    .subscribe({
                        val recordChoices = it.map {
                            CollectionRecordChoice(it, false)
                        }
                        Logger.e(DefaultGson.gsonForPrint.toJson(it))
                        collectionDetailView.showCollectionRecords(recordChoices)
                        collectionDetailView.showSuccess("获取成功")
                    }, {
                        collectionDetailView.showError("获取失败")
                    })
                    .let {
                        add(it)
                    }
        }
    }

    override fun handleCollectionChoice(records: List<CollectionRecordChoice>, weeks: String, isFullWeek: Boolean) {
        val collectionRecords = records.filter {
            it.isSelected
        }.map {
            it.collectionRecord
        }

        if (collectionRecords.isEmpty()) {
            collectionDetailView.showError("请选择用户")
            return
        }

        val weekList = mutableListOf<Int>()
        weeks.forEachIndexed { index, c ->
            if (c == '1') {
                weekList.add(index + 1)
            }
        }
        if (weekList.isEmpty()) {
            collectionDetailView.showError("请选择周数")
            return
        }


        val syllabus = if (isFullWeek)
            parseCollectionFullWeek(collectionRecords, weekList)
        else
            parseCollectionEveryWeek(collectionRecords, weekList)

        collectionDetailView.toSyllabusAct(DefaultGson.gson.toJson(syllabus))
    }

    override fun unsubscribe() {
        dispose()
    }

}