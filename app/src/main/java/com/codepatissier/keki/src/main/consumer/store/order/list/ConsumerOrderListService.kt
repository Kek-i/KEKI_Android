package com.codepatissier.keki.src.main.consumer.store.order.list;

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.store.order.list.model.ConsumerOrderListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerOrderListService(val consumerOrderListView: ConsumerOrderListView) {
    fun tryGetConsumerOrderList(orderStatus: String?){
        val consumerOrderListRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerOrderListRetrofitInterface::class.java)
        consumerOrderListRetrofitInterface.getConsumerOrderList(orderStatus).enqueue(object : Callback<ConsumerOrderListResponse>{
            override fun onResponse(
                call: Call<ConsumerOrderListResponse>,
                response : Response<ConsumerOrderListResponse>
            ) {
                consumerOrderListView.onGetConsumerOrderListSuccess(response.body() as ConsumerOrderListResponse)
            }

            override fun onFailure(call: Call<ConsumerOrderListResponse>, t: Throwable) {
                consumerOrderListView.onGetConsumerOrderListFailure(t.message ?: "통신 오류")
            }

        })
    }
}
