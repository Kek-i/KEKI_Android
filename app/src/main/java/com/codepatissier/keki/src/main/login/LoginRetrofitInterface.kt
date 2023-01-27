package com.codepatissier.keki.src.main.login

import com.codepatissier.keki.src.main.login.profilesetting.model.PostNickname
import com.codepatissier.keki.src.main.login.model.SocialLoginResponse
import com.codepatissier.keki.src.main.login.model.SocialTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @GET("/users/login/naver")
    fun getLoginNaverUrl(): Call<SocialLoginResponse>

    @GET("/users/callback/naver")
    fun callbackNaverToken(): Call<SocialTokenResponse>

    @GET("/users/login/kakao")
    fun getLoginKakaoUrl(): Call<SocialLoginResponse>

    @GET("/users/callback/kakao")
    fun callbackKakaoToken(): Call<SocialTokenResponse>

    @GET("/users/login/google")
    fun getLoginGoogleUrl(): Call<SocialLoginResponse>

    @GET("/users/callback/google")
    fun callbackGoogleToken(): Call<SocialTokenResponse>

}