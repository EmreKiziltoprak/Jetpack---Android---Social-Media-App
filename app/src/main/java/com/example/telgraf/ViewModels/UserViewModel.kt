package com.example.telgraf.ViewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.TextFieldValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.telgraf.Network.ApiClient
import com.example.telgraf.Network.Models.*
import com.example.telgraf.n.destinations.HomeScreenDestination
import com.google.gson.Gson
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


data class department(
    var id: Int,
    var name: String
)

class UserViewModel : ViewModel() {
    var isLoaded by mutableStateOf(false)
    private val postss = mutableListOf<PostData>()
    val postst = mutableStateOf(postss)

    var name by mutableStateOf(TextFieldValue(""))
    var surname by mutableStateOf(TextFieldValue(""))
    var schooln by mutableStateOf(TextFieldValue(""))
    var institude by mutableStateOf(TextFieldValue(""))
    var bio by mutableStateOf(TextFieldValue(""))

    var forgotPasswordSuccess by mutableStateOf(0)
    var emai by mutableStateOf(TextFieldValue(""))
    var pas by mutableStateOf(TextFieldValue(""))

    var user_id_flow = MutableStateFlow(-1)

    var departments = mutableListOf<department>()
    val department = mutableStateOf(departments)
    val registerErrorMessage = mutableStateOf("")
    var selecteddepartment: department? = null

    var isLoginSuccess: Boolean? = null
    var isAuthing = mutableStateOf(false)
    var registerError = mutableStateOf(false)

    val renewk = mutableStateOf(1)

    //USER_INFO
    var user: UserMainModel? = null
    var userInfo by mutableStateOf(user)

    //NOTIFICATIONS
    var l = listOf<NotificationsModelData>()
    var notifcationsList by mutableStateOf(l)

    //NOTIFICATIONS

    init {
        department.value.add(department(1, "Bilgisayar Mühendisliği"))
        department.value?.add(department(2, "Biyomedikal Mühendisliği"))
        department.value?.add(department(3, "Elektirik Elektronik Mühendisliği"))
        department.value?.add(department(4, "Enerji Mühendisliği"))
        department.value?.add(department(5, "Fizik Mühendisliği"))
        department.value?.add(department(6, "Gıda Mühendisliği"))
        department.value?.add(department(7, "Jeofizik Mühendisliği"))
        department.value?.add(department(8, "Jeoloji Mühendisliği"))
        department.value?.add(department(9, "Kimya Mühendisliği"))
    }


    suspend fun dologin(
        context: Context? = null,
        email: String,
        password: String,
        navigator: DestinationsNavigator? = null
    ) {


        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .login(
                loginBody = LoginBody(
                    email = email,
                    password = password
                )
            )
            .enqueue(object : Callback<LoginResponseModel> {

                override fun onResponse(
                    call: Call<LoginResponseModel>?,
                    response: Response<LoginResponseModel>?
                ) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */

                    if (response != null) {

                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            isLoginSuccess = true
                            token.value = response.body()?.accessToken ?: ""
                            if (context != null) {
                                viewModelScope.launch {
                                    response.body()?.accessToken?.let {
                                        saveToken(
                                            context = context,
                                            token = it
                                        )
                                    }

                                    context.let {
                                        getToken(context = it).collect { value ->
                                            token.value = value
                                            Log.d(
                                                "GET_TOKEN", token.value
                                            )
                                        }
                                    }
                                }
                                val jwt = JWT(token.value)
                                Log.d("JWT", "jwt.toString()")
                                Log.d("JWT", jwt.signature.toString())
                                var claim: String? = jwt.getClaim("userId").asString()
                                if (claim != null) {
                                    user_id_flow.value = claim.toInt()
                                    userId = claim.toInt()
                                }
                                Log.d("JWT", claim.toString())
                                Log.d("JWT", jwt.getClaim("userId").toString())

                            }
                            if (navigator != null) {
                                isLoginSuccess = null
                                isAuthing.value = false
                                navigator.navigate(HomeScreenDestination)
                            }

                        }
                        if (response.raw().code() == 400 || response.raw().code() == 401) {
                            isLoginSuccess = false
                            isAuthing.value = false
                            renewk.value = 13 + renewk.value
                        }
                    }


                }

                override fun onFailure(call: Call<LoginResponseModel>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }

    //MEZUN UYELİK
    suspend fun doGraduateRegister(
        context: Context? = null,
        body: GraduateRegisterBody
    ) {


        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .doRegisterGraduate(
                graduateRegisterBody = body
            )
            .enqueue(object : Callback<Any> {

                override fun onResponse(
                    call: Call<Any>?,
                    response: Response<Any>?
                ) {
                    val gson = Gson()
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */

                    if (response != null) {

                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            Log.d("GRADUATE_REGISTER_SUCCESS", response.raw().toString())
                            isAuthing.value = false
                            registerErrorMessage.value =
                                "Hesap başarıyla oluşturuldu\n Lütfen mail adresinize gelen linke tıklayarak onaylama işlemini tamamlayın"
                            registerError.value = true

                            name = TextFieldValue("")
                            schooln = TextFieldValue("")
                            surname = TextFieldValue("")
                            emai = TextFieldValue("")
                            pas = TextFieldValue("")

                        } else if (response.raw().code() == 400 || response.raw().code() == 401) {
                            Log.d("GRADUATE_REGISTER_ERROR", response.raw().toString())
                            isAuthing.value = false

                            val loginError: GraduateRegisterError = gson.fromJson(
                                response.errorBody()!!.string(),
                                GraduateRegisterError::class.java
                            )

                            if (loginError.message != "") {
                                registerErrorMessage.value = "Bu email adresi önceden alınmış"
                                registerError.value = true
                            }
                            Log.d("RESIGSTER_eRROR", loginError.toString())
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

    //OKUL MAİLİ İLE
    suspend fun doUserRegister(
        context: Context? = null,
        body: UserRegisterBody
    ) {


        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .doUserRegister(
                registerBody = body
            )
            .enqueue(object : Callback<Any> {

                override fun onResponse(
                    call: Call<Any>?,
                    response: Response<Any>?
                ) {
                    val gson = Gson()
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */

                    if (response != null) {

                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            Log.d("USER_REGISTER_SUCCESS", response.raw().toString())
                            isAuthing.value = false
                            registerErrorMessage.value =
                                "Hesap başarıyla oluşturuldu\n Lütfen mail adresinize gelen linke tıklayarak onaylama işlemini tamamlayın"
                            registerError.value = true

                            name = TextFieldValue("")
                            schooln = TextFieldValue("")
                            surname = TextFieldValue("")
                            emai = TextFieldValue("")
                            pas = TextFieldValue("")

                        } else if (response.raw().code() == 400 || response.raw().code() == 401) {
                            Log.d("GRADUATE_REGISTER_ERROR", response.raw().toString())
                            isAuthing.value = false

                            val loginError: GraduateRegisterError = gson.fromJson(
                                response.errorBody()!!.string(),
                                GraduateRegisterError::class.java
                            )

                            if (loginError.message != "") {
                                registerErrorMessage.value = "Bu email adresi önceden alınmış"
                                registerError.value = true
                            }
                            Log.d("RESIGSTER_eRROR", loginError.toString())
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

    suspend fun getUserInfo(
        context: Context? = null
    ) {
        Log.d("USER_INFO_STARTED", user_id_flow.value.toString())

        isLoginSuccess = true
        isAuthing.value = true
        ApiClient
            .getApiService()
            .getUserInfo(
                auth = "Bearer ${token.value}",
                id = user_id_flow.value.toString()
            )
            .enqueue(object : Callback<UserMainModel> {

                override fun onResponse(
                    call: Call<UserMainModel>?,
                    response: Response<UserMainModel>?
                ) {
                    val gson = Gson()
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */

                    if (response != null) {

                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            Log.d("USER_INFO", response.raw().toString())

                            Log.d("USER_INFO", response.body().toString())

                            userInfo = response.body()

                            name = TextFieldValue(userInfo!!.name.toString())
                            surname = TextFieldValue(userInfo!!.surname.toString())
                            bio = TextFieldValue(userInfo!!.bio ?: "")
                            emai = TextFieldValue(userInfo!!.email.toString())

                            if (userInfo!!.info != emptyList<Any>()) {
                                institude = TextFieldValue(userInfo!!.info?.get(0)?.institute ?: "")
                            }
                            isLoaded = true
                        } else if (response.raw().code() == 400 || response.raw().code() == 401) {
                            Log.d("USER_INFO_ERROR", response.raw().toString())

                        }
                    }
                }


                override fun onFailure(call: Call<UserMainModel>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }

    suspend fun getCustomUser(
        context: Context? = null,
        id: Int
    ) {


        ApiClient
            .getApiService()
            .getUserInfo(
                auth = "Bearer ${token.value}",
                id = id.toString()
            )
            .enqueue(object : Callback<UserMainModel> {

                override fun onResponse(
                    call: Call<UserMainModel>?,
                    response: Response<UserMainModel>?
                ) {
                    val gson = Gson()
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */

                    if (response != null) {

                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            Log.d("USER_INFO", response.raw().toString())

                            Log.d("USER_INFO", response.body().toString())

                            userInfo = response.body()

                            name = TextFieldValue(userInfo!!.name.toString())
                            surname = TextFieldValue(userInfo!!.surname.toString())
                            bio = TextFieldValue(userInfo!!.bio ?: "")
                            emai = TextFieldValue(userInfo!!.email.toString())

                            if (userInfo!!.info != emptyList<Any>()) {
                                institude = TextFieldValue(userInfo!!.info?.get(0)?.institute ?: "")
                            }
                            isLoaded = true
                        } else if (response.raw().code() == 400 || response.raw().code() == 401) {
                            Log.d("USER_INFO_ERROR", response.raw().toString())

                        }
                    }
                }


                override fun onFailure(call: Call<UserMainModel>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }
    //TODO EDIT USER INFO
    suspend fun editUserInfo(
        context: Context? = null,
        body: EditMainUserModel
    ) {
        Log.d("USER_INFO_EDIT", "********************")
        Log.d("USER_INFO_EDIT_BODY", body.toString())

        ApiClient
            .getApiService()
            .editMainUserInfo(
                auth = "Bearer ${token.value}",
                id = user_id_flow.value.toString(),
                body = body
            )
            .enqueue(object : Callback<UserMainModel> {

                override fun onResponse(
                    call: Call<UserMainModel>?,
                    response: Response<UserMainModel>?
                ) {
                    val gson = Gson()
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */
                    Log.d("USER_INFO_EDIT", response!!.raw().toString())

                    if (response != null) {

                        if (response.raw().code() == 200 || response.raw().code() == 201) {

                            Log.d("USER_INFO_SUCCES", response.raw().toString())


                        } else if (response.raw().code() == 400 || response.raw().code() == 401) {
                            Log.d("USER_INFO_ERROR", response.raw().toString())

                        }
                    }
                }

                override fun onFailure(call: Call<UserMainModel>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }

    fun getPosts(
        limit: Int,
        reset: Boolean = false,
        userId: Int?=null
    ) {
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

                            Log.d("USER_POSTS", postst.value.toString())
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

    fun forgotPassword(
        email : String
    ) {
        forgotPasswordSuccess = -2
        ApiClient
            .getApiService()
            .forgotPassword(
                forgotPBody = ForgotPBody(value = Value(email = email), notifierOptions = NotifierOptions())
            )
            .enqueue(object : Callback<Any> {

                override fun onResponse(
                    call: Call<Any>?,
                    response: Response<Any>?
                ) {

                    Log.d("SUCCESS_FORGOT_PASSWORD", response!!.raw().toString())

                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            forgotPasswordSuccess = 1
                            Log.d("SUCCESS_FORGOT_PASSWORD", response.raw().toString())
                        }
                        else{
                            forgotPasswordSuccess = -1

                            Log.d("ERROR_FORGOT_PASSWORD", response.raw().toString())

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


    fun getNotifications(
    ) {
        ApiClient
            .getApiService()
            .getNotifications(
                "Bearer ${token.value}",
                id = user_id_flow.value
            )
            .enqueue(object : Callback<NotificationsModel> {

                override fun onResponse(
                    call: Call<NotificationsModel>?,
                    response: Response<NotificationsModel>?
                ) {


                    if (response != null) {
                        if (response.raw().code() == 200 || response.raw().code() == 201) {
                            Log.d("USER_NOTIFICATIONS", response.body().toString())
                            notifcationsList = response.body()!!.data?: emptyList()
                            Log.d("USER_NOTIFICATIONS_STATE", notifcationsList.toString())

                        }
                        else{

                        }
                    }
                }

                override fun onFailure(call: Call<NotificationsModel>?, t: Throwable?) {
                    /*
                    Error callback
                    */
                    Log.d("DEBUG", t.toString())
                }
            })

    }
}

var userId: Int? = null
val user_id = MutableStateFlow<String>("")

val token = MutableStateFlow<String>("")
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

private val COUNTER_KEY = stringPreferencesKey("token")

suspend fun saveToken(context: Context, token: String) {
    context.dataStore.edit { pref ->
        pref[COUNTER_KEY] = token
    }
}

suspend fun getPreferences(context: Context): Flow<Preferences> {
    return context.dataStore.data
}

suspend fun getToken(context: Context): Flow<String> {
    return context.dataStore.data.map { preferences ->
        preferences[COUNTER_KEY] ?: ""
    }
}