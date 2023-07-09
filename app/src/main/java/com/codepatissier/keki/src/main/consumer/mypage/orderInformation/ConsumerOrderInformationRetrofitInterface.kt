package com.codepatissier.keki.src.main.consumer.mypage.orderInformation

import com.codepatissier.keki.src.main.consumer.mypage.orderInformation.model.ConsumerOrderInformationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ConsumerOrderInformationRetrofitInterface {
    @GET("/orders/{orderIdx}")
    fun getConsumerOrderInformation(
        @Path("orderIdx") orderIdx:Int
    ):Call<ConsumerOrderInformationResponse>
}