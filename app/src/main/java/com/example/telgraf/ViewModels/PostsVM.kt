package com.example.telgraf.ViewModels

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import com.example.telgraf.Network.ApiClient
import com.example.telgraf.Network.Models.*
import com.example.telgraf.Network.PostEditBody
import com.example.telgraf.n.destinations.HomeScreenDestination
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PostsVM : ViewModel() {
    var post_id by mutableStateOf(-1)

    var currentPostId: Int? = null
    var comment_text by mutableStateOf("")
    val bitmap =
        mutableStateOf<Bitmap?>(null)
    var body: MultipartBody.Part? = null
    var reqFile: RequestBody? = null
    var postdetail: Int? = null
    var postsSize: Int? = null
    var total: Int? = null
    var post: PostData? = null
    var isLoginSuccess: Boolean? = null
    var isAuthing = mutableStateOf(false)
    var itemS by mutableStateOf(post)
    var likeCount by mutableStateOf(0)
    val renewk = mutableStateOf(1)
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
    var isFetched = mutableStateOf(false)
    private val postss = mutableListOf<PostData>()
    val postst = mutableStateOf(postss)

    private val userpostss = mutableListOf<PostData>()
    val userpostst = mutableStateOf(userpostss)

    private val postsc = mutableListOf<PostCommentsData>()
    val postcomments = mutableStateOf(postsc)
    var singlePostid: Int? = null

    var singlePost: PostData? = null
    var singlePoststate = mutableStateOf(singlePost)

    //EDIT POST VARIABLES
    var isDeletedSuccessFully = mutableStateOf(false)
    var editPostText by mutableStateOf("")
    var editPost by mutableStateOf(false)

    var createPhoto by mutableStateOf(false)
    var requestFile: RequestBody? = null
    var file: File? = null
    var fileBody: MultipartBody.Part? = null

    //ANNOUNCEMENTS
    var announcementsList = mutableListOf<AnnouncementsModelData>()
    var announcementsState = mutableStateOf(announcementsList)

    var jannouncementsList = mutableListOf<JobAnnouncementsData>()
    var jannouncementsState = mutableStateOf(jannouncementsList)

    //CARD PROFILE ID
    var cardProfileId = mutableStateOf(-1)
    var ppsuccess = mutableStateOf(0)
    fun getPosts(
        limit: Int,
        reset: Boolean = false,
        userId: Int?=null
    ) {
        Log.d("USER_ID_POSTS", userId.toString())
        if (reset == true) {
            postst.value = mutableListOf<PostData>()
        }
        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .getPosts(
                "Bearer ${token.value}",
                limit = limit,
                userId = userId
            )
            .enqueue(object : Callback<PostsModel> {

                override fun onResponse(
                    call: Call<PostsModel>?,
                    response: Response<PostsModel>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */

                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            isLoginSuccess = true
                            postst.value = response.body()?.data as MutableList<PostData>
                            postsSize = (response.body()?.data as MutableList<PostData>).size
                            total = response.body()?.total
                            Log.d("posts", postst.value.toString())
                            if(userId!=-1){
                            Log.d("user_posts", postst.value.toString())}
                        }
                    }


                }

                override fun onFailure(call: Call<PostsModel>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }

    fun getUserPosts(
        limit: Int,
        reset: Boolean = false,
        userId: Int?=null
    ) {
        Log.d("USER_ID_POSTS", userId.toString())
        if (reset == true) {
            postst.value = mutableListOf<PostData>()
        }
        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .getPosts(
                "Bearer ${token.value}",
                limit = limit,
                userId = userId
            )
            .enqueue(object : Callback<PostsModel> {

                override fun onResponse(
                    call: Call<PostsModel>?,
                    response: Response<PostsModel>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */

                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            isLoginSuccess = true
                            userpostst.value = response.body()?.data as MutableList<PostData>
                            postsSize = (response.body()?.data as MutableList<PostData>).size
                            total = response.body()?.total
                            Log.d("posts", postst.value.toString())
                            if(userId!=-1){
                                Log.d("user_posts", postst.value.toString())}
                        }
                    }


                }

                override fun onFailure(call: Call<PostsModel>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }


    suspend fun getPostsComments(
        context: Context,
        post_id: Int
    ) {
        postcomments.value = mutableListOf<PostCommentsData>()
        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .getpostComments(
                "Bearer ${token.value}",
                id = post_id
            )
            .enqueue(object : Callback<PostComments> {

                override fun onResponse(
                    call: Call<PostComments>?,
                    response: Response<PostComments>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */

                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            isLoginSuccess = true
                            postcomments.value =
                                response.body()?.data as MutableList<PostCommentsData>
                            Log.d("POSTCOMMENTS", response.body().toString())
                        }
                    }


                }

                override fun onFailure(call: Call<PostComments>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }

    suspend fun LikePost(
        context: Context,
        post_id: Int
    ) {
        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .LikePost(
                "Bearer ${token.value}",
                postLikeBody = LikePostBody(postId = post_id)
            )
            .enqueue(object : Callback<Any> {

                override fun onResponse(
                    call: Call<Any>?,
                    response: Response<Any>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */

                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            Log.d("LIKE_POST", response.body().toString())
                        }
                    }


                }

                override fun onFailure(call: Call<Any>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }

    suspend fun removeLike(
        context: Context,
        post_id: Int
    ) {
        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .removeLike(
                "Bearer ${token.value}",
                postLikeBody = LikePostBody(postId = post_id)
            )
            .enqueue(object : Callback<Any> {

                override fun onResponse(
                    call: Call<Any>?,
                    response: Response<Any>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */
                    if (response != null) {
                        Log.d("REMOVE_LIKE", response.raw().toString())
                    }

                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            Log.d("POSTCOMMENTS", response.body().toString())
                        }
                    }


                }

                override fun onFailure(call: Call<Any>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }

    fun getPostWithId(
        context: Context,
        id: Int,
        isRenewing: Boolean = false
    ) {
        singlePoststate.value = null

        currentPostId = id
        isFetched.value = false
        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .getIndividualPost(
                "Bearer ${token.value}",
                id = id
            )
            .enqueue(object : Callback<PostData> {

                override fun onResponse(
                    call: Call<PostData>?,
                    response: Response<PostData>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */
                    if (response != null) {
                    }

                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {

                            Log.d("GET_WİTH_İD", response.body().toString())
                            Log.d("LIKE_COUNT_REAL", response.body()!!.likeCount!!.toString())

                            isLoginSuccess = true
                            singlePoststate.value = response.body() as PostData
                            Log.d("SINGLE_POST",singlePoststate.value.toString())
                            if (isRenewing) {
                                likeCount = response.body()!!.likeCount!!
                                isFetched.value = true
                                renewk.value = renewk.value + 45
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<PostData>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }

    suspend fun doComment(
        context: Context,
        post_id: Int,
        text: String
    ) {
        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .doComment(
                "Bearer ${token.value}",
                body = SendCommentBody(postId = post_id, text = text)
            )
            .enqueue(object : Callback<Any> {

                override fun onResponse(
                    call: Call<Any>?,
                    response: Response<Any>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */
                    if (response != null) {
                        Log.d("REMOVE_LIKE", response.raw().toString())
                    }

                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            Log.d("POSTCOMMENTS", response.body().toString())
                            comment_text = ""
                        }
                    }


                }

                override fun onFailure(call: Call<Any>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }

    //MARK:: DELETE
    suspend fun deletePost(
        context: Context,
        post_id: Int
    ) {
        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .deletePost(
                "Bearer ${token.value}",
                id = post_id
            )
            .enqueue(object : Callback<Any> {

                override fun onResponse(
                    call: Call<Any>?,
                    response: Response<Any>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */


                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            Log.d("DELETED", response.body().toString())
                            isDeletedSuccessFully.value = true
                            getPosts(limit = 5, reset = true)
                        }
                    }


                }

                override fun onFailure(call: Call<Any>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }

    //MARK:: EDIT
    suspend fun editPost(
        context: Context,
        post_id: String,
        post_text: String
    ) {
        Log.d("EDIT_POST", "\n id: ${post_id} \n text: ${post_text}")

        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .editPost(
                "Bearer ${token.value}",
                id = post_id.toInt(),
                postEditBody = PostEditBody(content = post_text)
            )
            .enqueue(object : Callback<Any> {

                override fun onResponse(
                    call: Call<Any>?,
                    response: Response<Any>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */
                    Log.d("EDIT_POST", response!!.raw().toString())


                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            Log.d("EDIT_POST_BODY", response.body().toString())
                            editPost = false
                            postst.value = mutableListOf<PostData>()
                            getPosts(limit = 5, reset = true)
                        }
                    }


                }

                override fun onFailure(call: Call<Any>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }

    fun sendPost(
        context: Context,
        text: String,
        photo: Boolean = false,
        navigator: DestinationsNavigator
    ) {


        Log.d("CREATE_PHOTO", createPhoto.toString())
        Log.d("PHOTO", body.toString())

        isFetched.value = false
        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .sendPost(

                "Bearer ${token.value}",
                body = SendPostBody(content = text)
            )
            .enqueue(object : Callback<SinglePost> {

                override fun onResponse(
                    call: Call<SinglePost>?,
                    response: Response<SinglePost>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */

                    if (response != null) {
                        Log.d("POST_SENT", response.body().toString())
                    }

                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            Log.d("POST_SENT", response.body().toString())
                            post_id = response.body()!!.id
                            if (file == null) {
                                navigator.navigate(HomeScreenDestination)
                            }
                            Log.d("POST_İD", post_id.toString())
                            if (post_id != -1) {
                                val role =
                                    RequestBody.create(MediaType.parse("text/plain"), "attachment")

                                val related_post =
                                    RequestBody.create(
                                        MediaType.parse("text/plain"),
                                        (post_id).toString()
                                    )

                                if (createPhoto)
                                    fileBody?.let {
                                        ApiClient
                                            .getApiService()
                                            .uploadPhoto(
                                                role = role,
                                                auth = "Bearer ${token.value}",
                                                post = related_post,
                                                image = it
                                            )
                                            .enqueue(object : Callback<okhttp3.ResponseBody> {

                                                override fun onResponse(
                                                    call: Call<okhttp3.ResponseBody>?,
                                                    response: Response<okhttp3.ResponseBody>?
                                                ) {
                                                    Log.d("SEND_PHOTO", response!!.raw().toString())
                                                    Log.d(
                                                        "SEND_PHOTO2",
                                                        response!!.body().toString()
                                                    )
                                                    Log.d(
                                                        "SEND_PHO3TO",
                                                        response!!.errorBody().toString()
                                                    )

                                                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                                                         */
                                                    if (response != null) {
                                                    }

                                                    if (response != null) {
                                                        if (response.raw()
                                                                .code() == 200 || response.raw()
                                                                .code() == 201
                                                        ) {

                                                            Log.d(
                                                                "SEND_PHOTO",
                                                                response.body().toString()
                                                            )
                                                            navigator.navigate(HomeScreenDestination)
                                                            file = null
                                                            bitmap.value = null
                                                        }
                                                    }
                                                }

                                                override fun onFailure(
                                                    call: Call<okhttp3.ResponseBody>?,
                                                    t: Throwable?
                                                ) {
                                                    /*
                                                        Error callback
                                                        */
                                                    Log.d("DEBUG", t.toString())
                                                }
                                            })
                                    }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<SinglePost>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })


    }

    fun changePP() {


        Log.d("CREATE_PHOTO", createPhoto.toString())
        Log.d("PHOTO", body.toString())

        isFetched.value = false
        isLoginSuccess = true
        isAuthing.value = true
        val role = RequestBody.create(MediaType.parse("text/plain"), "pp")

        val related_post =
            RequestBody.create(MediaType.parse("text/plain"), (post_id).toString())
        fileBody?.let {
        ApiClient
            .getApiService()
            .uploadPhoto(
                role = role,
                auth = "Bearer ${token.value}",
                post = related_post,
                image = it
            )
            .enqueue(object : Callback<okhttp3.ResponseBody> {

                override fun onResponse(
                    call: Call<okhttp3.ResponseBody>?,
                    response: Response<okhttp3.ResponseBody>?
                ) {
                    Log.d("SEND_PHOTO", response!!.raw().toString())
                    Log.d("SEND_PHOTO2", response!!.body().toString())
                    Log.d("SEND_PHO3TO", response!!.errorBody().toString())

                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                         */
                    if (response != null) {
                    }

                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            ppsuccess.value = 1
                            Log.d("SEND_PHOTO", response.body().toString())

                        }
                        else {
                            ppsuccess.value = -1
                        }
                    }
                }

                override fun onFailure(call: Call<okhttp3.ResponseBody>?, t: Throwable?) {
                    /*
                        Error callback
                        */
                    Log.d("DEBUG", t.toString())
                }
            })
    }
}

    fun getAnn(
    ) {

        ApiClient
            .getApiService()
            .getAnnouncements(
                "Bearer ${token.value}"
            )
            .enqueue(object : Callback<AnnouncementsModel> {

                override fun onResponse(
                    call: Call<AnnouncementsModel>?,
                    response: Response<AnnouncementsModel>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */

                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            announcementsState.value = response.body()?.data as MutableList<AnnouncementsModelData>
                            Log.d("ANN", announcementsState.value.toString())
                        }
                    }


                }

                override fun onFailure(call: Call<AnnouncementsModel>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }


    fun getJobAnn(
    ) {

        ApiClient
            .getApiService()
            .getJobAnnouncements(
                "Bearer ${token.value}"
            )
            .enqueue(object : Callback<JobAnnouncements> {

                override fun onResponse(
                    call: Call<JobAnnouncements>?,
                    response: Response<JobAnnouncements>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */

                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            jannouncementsState.value = response.body()?.data as MutableList<JobAnnouncementsData>
                            Log.d("ANN", jannouncementsState.value.toString())
                        }
                    }


                }

                override fun onFailure(call: Call<JobAnnouncements>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }
}
