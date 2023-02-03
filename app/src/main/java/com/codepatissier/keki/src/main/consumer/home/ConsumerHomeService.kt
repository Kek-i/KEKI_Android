package com.codepatissier.keki.src.main.consumer.home

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.home.model.ConsumerHomeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerHomeService(val consumerHomeView: ConsumerHomeView) {
    fun tryGetConsumerHome(){
        val consumerHomeRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerHomeRetrofitInterface::class.java)
        consumerHomeRetrofitInterface.getConsumerHome().enqueue(object: Callback<ConsumerHomeResponse>{
            override fun onResponse(
                call: Call<ConsumerHomeResponse>,
                response: Response<ConsumerHomeResponse>
            ) {
                consumerHomeView.onGetConsumerHomeSuccess(response.body() as ConsumerHomeResponse)
            }

            override fun onFailure(call: Call<ConsumerHomeResponse>, t: Throwable) {
                consumerHomeView.onGetConsumerHomeFailure(t.message ?: "통신 오류")
            }

        })
    }

}