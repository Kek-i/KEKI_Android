package com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail

import com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail.model.ConsumerStoreProductFeedDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ConsumerStoreProductFeedDetailRetrofitInterface {
    @GET("/desserts/{dessertIdx}")
    fun getProductFeedDetail(
        @Path("dessertIdx") dessertIdx:Long
        ):Call<ConsumerStoreProductFeedDetailResponse>
}