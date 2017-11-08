package com.daijie.ksyllabusapp.api

import com.daijie.ksyllabusapp.data.*
import retrofit2.http.*
import rx.Observable

/**
 * Created by daidaijie on 2017/10/22.
 */
interface SchoolDymaticApi {


    /**
     * @param type              0(默认值) 表示结果按照活动开始时间排序返回
     *                          1 表示结果按照发布时间排序返回
     * @param activityStartTime type 为 0 时才有意义
     *                          表示服务器根据activity_start_time为界限,
     *                          比如activity_start_time为 2016/8/16 那么返回的活动将会是
     *                          那些已经开始了但是开始时间晚于或者等于2016/8/16的活动
     *                          以及未来的并未开始的活动
     * @param pageIndex         页码
     * @param pageSize          每页的结果数
     * @return
     */
    @GET("interaction/api/v2.1/activity")
    fun getNotices(
            @Query("type") type: Int,
            @Query("page_index") pageIndex: Int,
            @Query("page_size") pageSize: Int
    ): Observable<JsonResponse<JsonList<SchoolNotice>>>

    @POST("interaction/api/v2.1/activity")
    fun postNotice(@Body schoolNoticeRequest: SchoolNoticeRequest): Observable<JsonResponse<Void>>

    @POST("interaction/api/v2.1/post")
    fun postCircle(@Body schoolCircleRequest: SchoolCircleRequest): Observable<JsonResponse<Void>>

    @DELETE("interaction/api/v2.1/post")
    fun deleteDymatic(@Header("id") post_id: Int, @Header("uid") uid: Int, @Header("token") token: String): Observable<JsonResponse<Void>>

    /**
     *  sort_type=2, 为降序
     *  @param count 每页的结果数
     *  @param before_id 在哪一页之前,使用无限大则可以获取第一列
     */
    @GET("interaction/api/v2.1/posts?sort_type=2")
    fun getCircles(@Query("count") count: Int, @Query("before_id") offset: Int)
            : Observable<JsonResponse<SchoolCircleList>>


    @POST("interaction/api/v2.1/like")
    fun like(@Body thumbUp: ThumbUp): Observable<JsonResponse<ThumbUpResponse>>


    @DELETE("interaction/api/v2.1/like")
    fun unlike(@Header("id") likeId: Int, @Header("uid") uid: Int, @Header("token") token: String): Observable<JsonResponse<Void>>

    @GET("interaction/api/v2.1/post_comments?field=post_id&count=${Int.MAX_VALUE}")
    fun getComments(@Query("value") postId: Int): Observable<JsonResponse<SchoolDymaticCommentList>>

    @POST("interaction/api/v2.1/comment")
    fun sendComment(@Body commentRequest: CommentRequest): Observable<JsonResponse<Void>>

    @DELETE("interaction/api/v2.1/comment")
    fun deleteComment(@Header("id") commentId: Int, @Header("uid") uid: Int, @Header("token") token: String): Observable<JsonResponse<Void>>
}