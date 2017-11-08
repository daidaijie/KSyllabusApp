package com.daijie.ksyllabusapp.ui.selectlesson.addshare

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.LessonChoice
import com.daijie.ksyllabusapp.data.ShareLessonData
import com.daijie.ksyllabusapp.ext.*
import com.daijie.ksyllabusapp.repository.lesson.Lesson
import com.daijie.ksyllabusapp.repository.lesson.LessonDataRepository
import com.daijie.ksyllabusapp.repository.semester.Semester
import com.daijie.ksyllabusapp.repository.semester.SemesterRepository
import com.daijie.ksyllabusapp.repository.user.User
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import com.daijie.ksyllabusapp.utils.DefaultGson
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/7.
 */
class AddSharePresenter @Inject constructor(
        private val userDataRepository: UserDataRepository,
        private val semesterRepository: SemesterRepository,
        private val lessonDataRepository: LessonDataRepository,
        private val addShareView: AddShareContract.View,
        private val subscriptioner: Subscriptioner
) : AddShareContract.Presenter, ISubscriptioner by subscriptioner {

    override fun addShareLessons(lessonChoices: List<LessonChoice>) {
        val shareLessons = lessonChoices.filter {
            it.isSelected
        }.map {
            it.lesson
        }

        if (shareLessons.isNotEmpty()) {
            addShareView.showLoadingDialog("正在添加课程至服务器")
            userDataRepository.currentUser.post(addShareView) { user ->
                lessonDataRepository.addLesson(user, semesterRepository.currentSemester, shareLessons)
                        .to_ui()
                        .onErrorToLogin(addShareView)
                        .subscribe({
                            addShareView.hideLoadingDialog()
                            addShareView.showSuccess("添加成功")
                        }, {
                            addShareView.hideLoadingDialog()
                            addShareView.showError("添加失败")
                        })
            }
        } else {
            addShareView.showError("请至少选择一项")
        }

    }

    override fun getShareById(objectId: String) {
        val semester = semesterRepository.currentSemester
        userDataRepository.currentUser.post(addShareView) { user ->
            addShareView.showLoadingDialog("正在从服务器获取分享的课程")
            lessonDataRepository.getShareLessonsById(objectId)
                    .to_ui()
                    .subscribe({
                        if (it.semester == semester.startYear.toString()
                                && it.season == semester.season.toString()) {
                            if (it.account == user.account) {
                                addShareView.handleSameAccount({
                                    handleData(user, semester, it)
                                }, {
                                    addShareView.hideLoadingDialog()
                                })
                            } else {
                                handleData(user, semester, it)
                            }
                        } else {
                            addShareView.hideLoadingDialog()
                            addShareView.showError("不能添加非当前学期课程")
                        }
                    }, {
                        addShareView.showError("获取失败")
                        addShareView.hideLoadingDialog()
                    })
                    .let {
                        add(it)
                    }
        }
    }

    private fun handleData(user: User, semester: Semester, shareLessonData: ShareLessonData) {
        lessonDataRepository.getLessons(user, semester, false)
                .to_computation()
                .map {
                    val lessonIdSet = mutableSetOf<String>()
                    it.forEach {
                        lessonIdSet.add(it.id)
                    }

                    val lessonChoices = DefaultGson.gson.fromJson<List<Lesson>>(shareLessonData.lessons).map {
                        val isExist = it.id in lessonIdSet
                        LessonChoice(it, isSelectable = !isExist)
                    }
                    lessonChoices
                }
                .to_ui()
                .subscribe({
                    addShareView.showData(it)
                    addShareView.hideLoadingDialog()
                }, {
                    addShareView.showError("课程解析失败")
                    addShareView.hideLoadingDialog()
                })
                .let {
                    add(it)
                }
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        dispose()
    }
}