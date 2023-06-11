package com.codepatissier.keki.src.main.seller.mypage.orderInformation

import com.codepatissier.keki.src.main.seller.mypage.orderInformation.model.SellerOrderInformationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SellerOrderInformationRetrofitInterface {
    @GET("/orders/{orderIdx}")
    fun getSellerOrderInformation(
        @Path("orderIdx") orderIdx:Int
    ):Call<SellerOrderInformationResponse>
}