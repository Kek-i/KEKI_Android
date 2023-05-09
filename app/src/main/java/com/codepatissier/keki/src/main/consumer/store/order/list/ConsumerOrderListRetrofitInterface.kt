package com.codepatissier.keki.src.main.consumer.store.order.list;

import com.codepatissier.keki.src.main.consumer.store.order.list.model.ConsumerOrderListResponse
import retrofit2.Call
import retrofit2.http.GET;
import retrofit2.http.Query

interface ConsumerOrderListRetrofitInterface {
    @GET("/orders/history")
    fun getConsumerOrderList(
        @Query("orderStatus") orderStatus: String?
    ): Call<ConsumerOrderListResponse>
}
