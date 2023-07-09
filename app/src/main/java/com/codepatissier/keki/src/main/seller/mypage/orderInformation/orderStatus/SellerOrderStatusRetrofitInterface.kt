package com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus

import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.model.SellerOrderStatusEditBody
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.model.SellerOrderStatusEditResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface SellerOrderStatusRetrofitInterface {
    @PATCH("/orders/status")
    fun patchOrderStatus(
        @Body parameter : SellerOrderStatusEditBody
    ) : Call<SellerOrderStatusEditResponse>
}