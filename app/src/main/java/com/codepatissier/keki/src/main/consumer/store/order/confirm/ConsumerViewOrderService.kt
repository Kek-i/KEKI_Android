package com.codepatissier.keki.src.main.consumer.store.order.confirm

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.store.order.confirm.model.ConsumerViewOrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerViewOrderService(val consumerViewOrderView: ConsumerViewOrderView) {
    fun tryGetConsumerViewOrder(storeIdx: Long){
        val consumerViewOrderRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerViewOrderRetrofitInterface::class.java)
        consumerViewOrderRetrofitInterface.getConsumerViewOrder(storeIdx).enqueue(object: Callback<ConsumerViewOrderResponse>{
            override fun onResponse(
                call: Call<ConsumerViewOrderResponse>,
                response: Response<ConsumerViewOrderResponse>
            ) {
                consumerViewOrderView.onGetConsumerViewOrderSuccess(response.body() as ConsumerViewOrderResponse)
            }

            override fun onFailure(call: Call<ConsumerViewOrderResponse>, t: Throwable) {
                consumerViewOrderView.onGetConsumerViewOrderFailure(t.message ?: "통신 오류")
            }

        })
    }
}