package com.example.telgraf.Network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val okHttpClient = OkHttpClient.Builder()
    .readTimeout(60, TimeUnit.SECONDS)
    .connectTimeout(60, TimeUnit.SECONDS)
    .build()


object Constant {
    var baseUrl: String = "https://telgraf.ankara.edu.tr/api/"

}
//https://telgraf.ankara.edu.tr/api/
//locale http://192.168.0.10:3030
object ApiClient {
    private lateinit var apiService: ApiService

    fun getApiService(): ApiService {

        // Initialize ApiService if not initialized yet
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService
    }

}