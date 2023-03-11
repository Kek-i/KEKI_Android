package com.codepatissier.keki.src.main.seller.store.productfeed

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail.model.ConsumerStoreProductFeedDetailResponse
import com.codepatissier.keki.src.main.seller.store.productfeed.product.model.SellerProductAddBody
import com.codepatissier.keki.src.main.seller.store.productfeed.product.model.SellerProductAddResponse
import com.codepatissier.keki.src.main.seller.store.productfeed.model.SellerProductFeedDetailResponse
import retrofit2.Call
import retrofit2.http.*

interface SellerProductFeedDetailRetrofitInterface {
    @GET("/desserts/{dessertIdx}/editDessert")
    fun getProductFeedDetail(
        @Path("dessertIdx") dessertIdx:Long
        ):Call<SellerProductFeedDetailResponse>

    @PATCH("/desserts/{dessertIdx}")
    fun patchProductFeedDetail(
        @Path("dessertIdx") dessertIdx:Long,
        @Body params: SellerProductAddBody
    ) : Call<BaseResponse>


    @DELETE("/desserts/{dessertIdx}")
    fun delProductFeedDetail(
        @Path("dessertIdx") dessertIdx:Long
    ):Call<BaseResponse>
}