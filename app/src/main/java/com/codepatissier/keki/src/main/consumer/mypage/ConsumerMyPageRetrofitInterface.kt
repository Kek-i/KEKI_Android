package com.codepatissier.keki.src.main.consumer.mypage

import com.codepatissier.keki.src.main.consumer.mypage.model.ConsumerMyPageResponse
import retrofit2.Call
import retrofit2.http.GET

interface ConsumerMyPageRetrofitInterface {
    @GET("/users/profile")
    fun getMyPage(): Call<ConsumerMyPageResponse>
}