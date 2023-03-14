package com.codepatissier.keki.src.main.seller.store.storefeed.storeedit

import com.codepatissier.keki.src.main.seller.store.storefeed.storeedit.model.SellerFeedEditViewResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SellerStoreFeedEditRetrofitInterface {
    @GET("/posts/{postIdx}/editView")
    fun getStoreFeedEditView(
        @Path("postIdx") postIdx: Long
    ): Call<SellerFeedEditViewResponse>
}