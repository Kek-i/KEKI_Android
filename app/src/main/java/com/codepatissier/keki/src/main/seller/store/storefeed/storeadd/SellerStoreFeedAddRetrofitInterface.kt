package com.codepatissier.keki.src.main.seller.store.storefeed.storeadd

import com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model.SellerFeedAddViewResponse
import retrofit2.Call
import retrofit2.http.GET

interface SellerStoreFeedAddRetrofitInterface {
    @GET("/posts/makeView")
    fun getStoreFeedAddView(): Call<SellerFeedAddViewResponse>
}