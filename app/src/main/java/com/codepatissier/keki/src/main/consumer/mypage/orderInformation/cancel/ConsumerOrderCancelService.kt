package com.codepatissier.keki.src.main.consumer.mypage.orderInformation.cancel

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.mypage.orderInformation.cancel.model.ConsumerOrderCancelResponse
import com.codepatissier.keki.src.main.consumer.mypage.orderInformation.model.ConsumerOrderInformationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerOrderCancelService(val consumerOrderCancelView: ConsumerOrderCancelView) {
    fun tryGetConsumerCancelInformation(orderIdx:Int){
        val consumerOrderCancelRetrofitInterface  = ApplicationClass.sRetrofit.create(ConsumerOrderCancelRetrofitInterface::class.java)
        consumerOrderCancelRetrofitInterface.getConsumerOrderCancel(orderIdx).enqueue(object:Callback<ConsumerOrderCancelResponse>{
            override fun onResponse(
                call: Call<ConsumerOrderCancelResponse>,
                response: Response<ConsumerOrderCancelResponse>
            ) {
                consumerOrderCancelView.onGetConsumerOrderCancelSuccess(response.body() as ConsumerOrderCancelResponse)
            }
            override fun onFailure(call: Call<ConsumerOrderCancelResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}