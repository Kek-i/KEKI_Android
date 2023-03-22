package com.codepatissier.keki.src.main.seller.store.productfeed.productadd

import com.codepatissier.keki.src.main.seller.store.productfeed.productadd.model.SellerProductAddBody
import com.codepatissier.keki.src.main.seller.store.productfeed.productadd.model.SellerProductAddResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SellerProductAddRetrofitInterface {
    @POST("/desserts")
    fun postProduct(
        @Body params: SellerProductAddBody
    ) : Call<SellerProductAddResponse>
}