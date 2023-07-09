package com.codepatissier.keki.src.main.consumer.mypage.orderInformation

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.mypage.orderInformation.model.ConsumerOrderInformationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerOrderInformationService(val consumerOrderInformationView: ConsumerOrderInformationView) {
    fun tryGetConsumerOrderInformation(orderIdx:Int){
        val consumerOrderInformationRetrofitInterface  = ApplicationClass.sRetrofit.create(ConsumerOrderInformationRetrofitInterface::class.java)
        consumerOrderInformationRetrofitInterface.getConsumerOrderInformation(orderIdx).enqueue(object:Callback<ConsumerOrderInformationResponse>{
            override fun onResponse(
                call: Call<ConsumerOrderInformationResponse>,
                response: Response<ConsumerOrderInformationResponse>
            ) {
                consumerOrderInformationView.onGetConsumerOrderInformationSuccess(response.body() as ConsumerOrderInformationResponse)
            }

            override fun onFailure(call: Call<ConsumerOrderInformationResponse>, t: Throwable) {
                consumerOrderInformationView.onGetConsumerOrderInformationFailure(t.message ?: "통신 오류")
            }

        })
    }
}