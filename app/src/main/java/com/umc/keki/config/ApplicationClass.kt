package com.umc.keki.config

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    val API_URL = "https://edu-api-test.softsquared.com/" // 변경

    companion object {
        lateinit var sSharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor

        val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"

        lateinit var sRetrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        sSharedPreferences =
            applicationContext.getSharedPreferences("KEKI_APP", MODE_PRIVATE) // 변경
        initRetrofitInstance()
    }

    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor()) // X_ACCESS_TOKEN 이름 변경되면 XAccessTokenInterceptor()에서 바꾸기
            .build()

        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}