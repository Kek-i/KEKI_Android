package com.codepatissier.keki.src.main.consumer.store.storefeed

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ConsumerStoreFeedDetailRetrofitInterface {
    @GET("/posts")
    fun getConsumerStoreFeedDetail(
        @Query("storeIdx") storeIdx: Long,
        @Query("cursorIdx") cursorIdx: Int?,
        @Query("size") size: Int?
    ): Call<ConsumerStoreDetailFeedResponse>

    @POST("/posts/{postIdx}/like")
    fun postConsumerStoreFeedDetailLike(
        @Path("postIdx") postIdx: Long
    ): Call<BaseResponse>
}