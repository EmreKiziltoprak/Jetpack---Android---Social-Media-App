package com.example.telgraf.Network

import com.example.telgraf.Network.Models.*
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @POST("authentication")
    fun login(
        @Body loginBody: LoginBody
    ): Call<LoginResponseModel>

    @GET("posts")
    fun getPosts(
        @Header("Authorization") auth: String,
        @Query("\$limit") limit: Int,
        @Query("userId") userId: Int?=null

    ): Call<PostsModel>

    @GET("posts")
    fun getUserPosts(
        @Header("Authorization") auth: String,
        @Query("\$limit") limit: Int,
        @Query("userId") userId: Int?=null

    ): Call<PostsModel>

    @GET("posts/{id}")
    fun getIndividualPost(
        @Header("Authorization") auth: String,
        @Path("id") id: Int?=null
    ): Call<PostData>


    @DELETE("posts/{id}")
    fun deletePost(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): Call<Any>


    @PUT("posts/{id}")
    fun editPost(
        @Header("Authorization") auth: String,
        @Path("id") id: Int,
        @Body postEditBody : PostEditBody
    ): Call<Any>

    @GET("comments")
    fun getpostComments(
        @Header("Authorization") auth: String,
        @Query("postId") id: Int
    ): Call<PostComments>

    @POST("graduates")
    fun doRegisterGraduate(
        @Body graduateRegisterBody: GraduateRegisterBody
    ): Call<Any>


    @POST("users")
    fun doUserRegister(
        @Body registerBody: UserRegisterBody
    ): Call<Any>

    @POST("likes")
    fun LikePost(
        @Header("Authorization") auth: String,
        @Body postLikeBody: LikePostBody
    ): Call<Any>


    @POST("comments")
    fun doComment(
        @Header("Authorization") auth: String,
        @Body body: SendCommentBody
    ): Call<Any>

    @HTTP(method = "DELETE", path = "likes", hasBody = true)
    fun removeLike(
        @Header("Authorization") auth: String,
        @Body postLikeBody: LikePostBody
    ): Call<Any>

    @POST("posts")
    fun sendPost(
        @Header("Authorization") auth: String,
        @Body body: SendPostBody
    ): Call<SinglePost>

    @Multipart
    @POST("uploads")
    fun uploadPhoto(
        @Header("Authorization") auth: String,
                      @Part("role") role: RequestBody,
                      @Part("related_post") post: RequestBody?=null,
                      @Part image: MultipartBody.Part): Call<ResponseBody>


    //USER SERVİCE
    @GET("users/{id}")
    fun getUserInfo(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): Call<UserMainModel>

    @GET("users/{id}")
    fun getMainUserInfo(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): Call<UserModel>

    @PUT("user-info/{id}")
    fun editUserInfo(
        @Header("Authorization") auth: String,
        @Path("id") id: String,
        @Body body: EditUserInfoModel
    ): Call<UserModel>

    @PATCH("users/{id}")
    fun editMainUserInfo(
        @Header("Authorization") auth: String,
        @Path("id") id: String,
        @Body body: EditMainUserModel
    ): Call<UserMainModel>

    //ANNOUNCEMENTS SERVİCE
    @GET("announcements")
    fun getAnnouncements(
        @Header("Authorization") auth: String
    ): Call<AnnouncementsModel>

    //JOB ANNOUNCEMENTS SERVİCE
    @GET("jobAnnouncements")
    fun getJobAnnouncements(
        @Header("Authorization") auth: String
    ): Call<JobAnnouncements>

    //NOTIFICATIONS
    @GET("notifications")
    fun getNotifications(
        @Header("Authorization") auth: String,
        @Query("recipientId") id: Int
    ): Call<NotificationsModel>

    //NOTIFICATIONS
    @POST("authManagement")
    fun forgotPassword(
        @Body forgotPBody: ForgotPBody
    ): Call<Any>
}


data class PostEditBody(
    @SerializedName("content")
    var content: String
)
