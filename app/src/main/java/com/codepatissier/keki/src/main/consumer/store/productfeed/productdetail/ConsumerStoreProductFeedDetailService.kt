package com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.store.productfeed.ConsumerStoreProductFeedRetrofitInterface
import com.codepatissier.keki.src.main.consumer.store.productfeed.model.ConsumerStoreProductFeedResponse
import com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail.model.ConsumerStoreProductFeedDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerStoreProductFeedDetailService(val consumerStoreProductFeedDetailView: ConsumerStoreProductFeedDetailView) {
    fun tryGetProductFeedDetail(dessertIdx:Long){
        val consumerStoreProductFeedDetailRetrofitInterface = ApplicationClass.sRetrofit.create(
            ConsumerStoreProductFeedDetailRetrofitInterface::class.java)
        consumerStoreProductFeedDetailRetrofitInterface.getProductFeedDetail(dessertIdx = dessertIdx).enqueue(object :Callback<ConsumerStoreProductFeedDetailResponse>{
            override fun onResponse(call: Call<ConsumerStoreProductFeedDetailResponse>, response: Response<ConsumerStoreProductFeedDetailResponse>) {
                consumerStoreProductFeedDetailView.onGetProductFeedSuccess(response.body() as ConsumerStoreProductFeedDetailResponse)
            }
            override fun onFailure(call: Call<ConsumerStoreProductFeedDetailResponse>, t: Throwable) {
                consumerStoreProductFeedDetailView.onGetProductFeedFailure(t.message ?: "통신 오류")
            }

        })
    }

}