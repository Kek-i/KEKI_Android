package com.codepatissier.keki.src.main.seller.mypage

import com.codepatissier.keki.src.main.consumer.mypage.model.ConsumerMyPageResponse
import com.codepatissier.keki.src.main.seller.mypage.model.SellerMyPageResponse
import retrofit2.Call
import retrofit2.http.GET

interface SellerMyPageRetrofitInterface {
    @GET("/stores/profile")
    fun getMyPage(): Call<SellerMyPageResponse>
}