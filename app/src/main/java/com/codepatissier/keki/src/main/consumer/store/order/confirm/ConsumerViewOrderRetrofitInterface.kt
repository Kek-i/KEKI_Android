package com.codepatissier.keki.src.main.consumer.store.order.confirm

import com.codepatissier.keki.src.main.consumer.store.order.confirm.model.ConsumerViewOrderResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ConsumerViewOrderRetrofitInterface {
    @GET("/orders/view/{storeIdx}")
    fun getConsumerViewOrder(
        @Path("storeIdx") storeIdx: Long
    ): Call<ConsumerViewOrderResponse>
}