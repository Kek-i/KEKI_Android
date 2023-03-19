package com.codepatissier.keki.src.main.seller.store.storefeed.storedetail.delete

import com.codepatissier.keki.config.BaseResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Path

interface SellerStoreFeedDetailDeleteRetrofitInterface {
    @DELETE("/posts/{postIdx}")
    fun deleteSellerStoreFeedDeatil(
        @Path("postIdx") postIdx: Long
    ): Call<BaseResponse>

}