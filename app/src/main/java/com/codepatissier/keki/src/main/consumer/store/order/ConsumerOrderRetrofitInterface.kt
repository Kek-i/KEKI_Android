package com.codepatissier.keki.src.main.consumer.store.order

import com.codepatissier.keki.src.main.consumer.store.order.model.ConsumerGetOrderScreenResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ConsumerOrderRetrofitInterface {
    @GET("/orders/view/{storeIdx}")
    fun getConsumerOrderScreen(
        @Path("storeIdx") storeIdx: Long
    ): Call<ConsumerGetOrderScreenResponse>
}