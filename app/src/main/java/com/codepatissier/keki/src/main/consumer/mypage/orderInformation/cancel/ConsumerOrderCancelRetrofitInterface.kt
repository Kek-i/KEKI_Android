package com.codepatissier.keki.src.main.consumer.mypage.orderInformation.cancel

import com.codepatissier.keki.src.main.consumer.mypage.orderInformation.cancel.model.ConsumerOrderCancelResponse
import retrofit2.Call
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ConsumerOrderCancelRetrofitInterface {
    @PATCH("/orders/cancel/{orderIdx}")
    fun getConsumerOrderCancel(
        @Path("orderIdx") orderIdx:Int
    ):Call<ConsumerOrderCancelResponse>
}