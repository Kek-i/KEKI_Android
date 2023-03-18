package com.codepatissier.keki.src.main.seller.store.storefeed.storeedit

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.seller.store.storefeed.storeedit.model.SellerFeedEditViewResponse
import com.codepatissier.keki.src.main.seller.store.storefeed.storeedit.model.UpdateStoreFeedRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SellerStoreFeedEditRetrofitInterface {
    @GET("/posts/{postIdx}/editView")
    fun getStoreFeedEditView(
        @Path("postIdx") postIdx: Long
    ): Call<SellerFeedEditViewResponse>

    @PATCH("/posts/{postIdx}")
    fun updateStoreFeed(
        @Path("postIdx") postIdx: Long,
        @Body params: UpdateStoreFeedRequest
    ): Call<BaseResponse>
}