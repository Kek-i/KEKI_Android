package com.codepatissier.keki.src.main.consumer.store.info

import com.codepatissier.keki.src.main.consumer.store.info.model.ConsumerStoreInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ConsumerStoreInfoRetrofitInterface {
    @GET("/stores/store-info/{storeIdx}")
    fun getStoreMainInfo(
        @Path("storeIdx") storeIdx:Long
    ): Call<ConsumerStoreInfoResponse>
}