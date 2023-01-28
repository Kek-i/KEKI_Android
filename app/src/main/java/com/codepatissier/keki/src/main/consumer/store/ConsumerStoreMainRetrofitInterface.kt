package com.codepatissier.keki.src.main.consumer.store

import com.codepatissier.keki.src.main.consumer.store.model.ConsumerStoreMainResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ConsumerStoreMainRetrofitInterface {
    @GET("/stores/profile/{storeIdx}")
    fun getStoreMain(
        @Path("storeIdx") storeIdx:Long
    ): Call<ConsumerStoreMainResponse>
}