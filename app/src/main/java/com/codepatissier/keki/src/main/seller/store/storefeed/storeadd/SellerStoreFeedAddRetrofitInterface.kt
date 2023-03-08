package com.codepatissier.keki.src.main.seller.store.storefeed.storeadd

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model.PostStoreFeedRequest
import com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model.SellerFeedAddViewResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SellerStoreFeedAddRetrofitInterface {
    @GET("/posts/makeView")
    fun getStoreFeedAddView(): Call<SellerFeedAddViewResponse>

    @POST("/posts")
    fun postStoreFeed(
        @Body params: PostStoreFeedRequest
    ): Call<BaseResponse>
}