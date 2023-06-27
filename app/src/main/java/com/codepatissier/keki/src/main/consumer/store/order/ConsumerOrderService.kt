package com.codepatissier.keki.src.main.consumer.store.order

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.store.order.model.ConsumerGetOrderScreenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerOrderService(val consumerOrderView: ConsumerOrderView) {
    fun tryGetConsumerOrderScreen(storeIdx: Long){
        val consumerScreenOrderRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerOrderRetrofitInterface::class.java)
        consumerScreenOrderRetrofitInterface.getConsumerOrderScreen(storeIdx).enqueue(object : Callback<ConsumerGetOrderScreenResponse>{
            override fun onResponse(
                call: Call<ConsumerGetOrderScreenResponse>,
                response: Response<ConsumerGetOrderScreenResponse>
            ) {
                consumerOrderView.onGetConsumerOrderScreenSuccess(response.body() as ConsumerGetOrderScreenResponse)
            }

            override fun onFailure(call: Call<ConsumerGetOrderScreenResponse>, t: Throwable) {
                consumerOrderView.onGetConsumerOrderScreenFailure(t.message ?: "통신 오류")
            }

        })
    }
}