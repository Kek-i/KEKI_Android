package com.codepatissier.keki.src.main.consumer.home.onefeed

import com.codepatissier.keki.src.main.consumer.home.onefeed.model.ConsumerOneFeedDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ConsumerOneFeedDetailRetrofitInterface {
    @GET("/posts/{postIdx}")
    fun getConsumerOneFeedDetail(
        @Path("postIdx") postIdx: Int
    ): Call<ConsumerOneFeedDetailResponse>
}