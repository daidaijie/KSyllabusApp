package com.daijie.ksyllabusapp.repository.schooldymatic.remote

import com.daijie.ksyllabusapp.api.SchoolDymaticApi
import com.daijie.ksyllabusapp.data.CommentRequest
import com.daijie.ksyllabusapp.data.SchoolDymaticComment
import com.daijie.ksyllabusapp.data.ThumbUp
import com.daijie.ksyllabusapp.ext.from_io
import com.daijie.ksyllabusapp.ext.takeData
import com.daijie.ksyllabusapp.repository.schooldymatic.SchoolDymaticDataSource
import com.daijie.ksyllabusapp.repository.user.User
import rx.Observable

/**
 * Created by daidaijie on 17-10-24.
 */

class SchoolDymaticRemoteDataSource(private val schoolDymaticApi: SchoolDymaticApi)
    : SchoolDymaticDataSource {

    override fun like(user: User, postId: Int): Observable<Int> {
        return schoolDymaticApi.like(ThumbUp(postId, user.id, user.token))
                .from_io()
                .takeData()
                .map {
                    it.id
                }
    }

    override fun unlike(user: User, likeId: Int): Observable<Void> {
        return schoolDymaticApi.unlike(likeId, user.id, user.token)
                .from_io()
                .takeData()
    }

    override fun getComments(postId: Int): Observable<List<SchoolDymaticComment>> {
        return schoolDymaticApi.getComments(postId)
                .from_io()
                .takeData()
                .map {
                    it.comments
                }
    }

    override fun sendComment(user: User, postId: Int, comment: String): Observable<Void> {
        return schoolDymaticApi.sendComment(CommentRequest(comment, user.token, postId, user.id))
                .from_io()
                .takeData()
    }

    override fun deleteComment(user: User, commentId: Int): Observable<Void> {
        return schoolDymaticApi.deleteComment(commentId, user.id, user.token)
                .from_io()
                .takeData()
    }


    override fun deleteSchoolDymatic(user: User, postId: Int): Observable<Void> {
        return schoolDymaticApi.deleteDymatic(postId, user.id, user.token)
                .from_io()
                .takeData()
    }

    override fun isCommentsDataSourceEmpty(errMsg: Throwable) = errMsg.message == "no resources yet"


}