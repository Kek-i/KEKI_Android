package com.codepatissier.keki.src.main.seller.mypage.orderlist

import com.codepatissier.keki.src.main.seller.mypage.orderlist.model.SellerOrderListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SellerOrderListRetrofitInterface {
    @GET("/orders/history")
    fun getSellerOrderList(
        @Query("orderStatus") orderStatus: String?
    ): Call<SellerOrderListResponse>
}