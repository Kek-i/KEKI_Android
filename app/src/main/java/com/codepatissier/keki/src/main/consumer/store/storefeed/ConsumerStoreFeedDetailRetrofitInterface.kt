package com.codepatissier.keki.src.main.consumer.store.storefeed

import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ConsumerStoreFeedDetailRetrofitInterface {
    @GET("/posts")
    fun getConsumerStoreFeedDetail(
        @Query("searchTag") searchTag: String,
        @Query("cursorIdx") cursorIdx: Int?,
        @Query("size") size: Int?
    ): Call<ConsumerStoreDetailFeedResponse>
}