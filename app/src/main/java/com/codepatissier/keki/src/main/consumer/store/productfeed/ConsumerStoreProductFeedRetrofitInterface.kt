package com.codepatissier.keki.src.main.consumer.store.productfeed

import com.codepatissier.keki.src.main.consumer.store.productfeed.model.ConsumerStoreProductFeedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ConsumerStoreProductFeedRetrofitInterface {
    @GET("/desserts?")
    fun getProductFeed(
        @Query("storeIdx") storeIdx:Long? = null,
        @Query("cursorIdx") cursorIdx:Long? = null,
        @Query("size") size:Int? = null
        ):Call<ConsumerStoreProductFeedResponse>
}