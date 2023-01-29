package com.codepatissier.keki.config

import android.app.Application
import android.content.SharedPreferences
import com.codepatissier.keki.R
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApplicationClass : Application() {
    val API_URL = "https://keki-dev.store/"

    companion object {
        lateinit var sSharedPreferences: SharedPreferences
        lateinit var userInfo: SharedPreferences.Editor
        val Authorization = "Authorization"
        val UserRole = "UserRole"
        lateinit var sRetrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        sSharedPreferences =
            applicationContext.getSharedPreferences("USER_INFO", MODE_PRIVATE) // 변경
        userInfo = sSharedPreferences.edit()
        initRetrofitInstance()

        //카카오 초기화
        KakaoSdk.init(this, getString(R.string.kakao_native_key))
        //네이버 초기화
        val naverClientId = getString(R.string.social_login_info_naver_client_id)
        val naverClientSecret = getString(R.string.social_login_info_naver_client_secret)
        val naverClientName = getString(R.string.social_login_info_naver_client_name)
        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret , naverClientName)


    }

    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor())
            .build()

        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}