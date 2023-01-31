package com.codepatissier.keki.src.main.consumer.store.info

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.store.info.model.ConsumerStoreInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerStoreInfoService(val consumerStoreInfoView: ConsumerStoreInfoView) {

    fun tryGetStoreInfo(storeIdx:Long){
        val consumerStoreInfoRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerStoreInfoRetrofitInterface::class.java)
        consumerStoreInfoRetrofitInterface.getStoreMainInfo(storeIdx).enqueue(object :Callback<ConsumerStoreInfoResponse>{
            override fun onResponse(
                call: Call<ConsumerStoreInfoResponse>,
                response: Response<ConsumerStoreInfoResponse>
            ) {
                consumerStoreInfoView.onGetStoreInfoSuccess(response.body() as ConsumerStoreInfoResponse)
            }

            override fun onFailure(call: Call<ConsumerStoreInfoResponse>, t: Throwable) {
                consumerStoreInfoView.onGetStoreInfoFailure(t.message ?: "통신 오류")
           }

        })
    }
}