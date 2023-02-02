package com.codepatissier.keki.src.main.consumer.store

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.store.model.ConsumerStoreMainResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerStoreMainService(val consumerStoreMainView: ConsumerStoreMainView) {

    fun tryGetStoreMain(storeIdx:Long){
        val consumerStoreMainRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerStoreMainRetrofitInterface::class.java)
        consumerStoreMainRetrofitInterface.getStoreMain(storeIdx).enqueue(object:Callback<ConsumerStoreMainResponse> {
            override fun onResponse(
                call: Call<ConsumerStoreMainResponse>,
                response: Response<ConsumerStoreMainResponse>
            ) {
                consumerStoreMainView.onGetStoreMainSuccess(response.body() as ConsumerStoreMainResponse)
            }

            override fun onFailure(call: Call<ConsumerStoreMainResponse>, t: Throwable) {
                consumerStoreMainView.onGetStoreMainFailure(t.message ?: "통신 오류")
            }

        })
    }
}