package com.daijie.ksyllabusapp.repository.schooldymatic

import com.daijie.ksyllabusapp.data.SchoolDymaticComment
import com.daijie.ksyllabusapp.repository.user.User
import rx.Observable

/**
 * Created by daidaijie on 17-10-24.
 */
interface SchoolDymaticDataSource {

    fun like(user: User, postId: Int): Observable<Int>

    fun unlike(user: User, likeId: Int): Observable<Void>

    fun getComments(postId: Int): Observable<List<SchoolDymaticComment>>

    fun sendComment(user: User, postId: Int, comment: String): Observable<Void>

    fun deleteComment(user: User, commentId: Int): Observable<Void>

    fun isCommentsDataSourceEmpty(errMsg: Throwable): Boolean

    fun deleteSchoolDymatic(user: User, postId: Int): Observable<Void>
}