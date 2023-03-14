package com.codepatissier.keki.src.main.seller.store.storefeed.detail

import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SellerStoreFeedDetailRetrofitInterface {
    @GET("/posts")
    fun getSellerStoreFeedDetail(
        @Query("storeIdx") storeIdx: Long,
        @Query("cursorIdx") cursorIdx: Int?,
        @Query("size") size: Int?
    ): Call<ConsumerStoreDetailFeedResponse>
}