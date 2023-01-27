package com.codepatissier.keki.src.main.consumer.store.productfeed

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.store.productfeed.model.ConsumerStoreProductFeedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerStoreProductFeedService(val consumerStoreProductFeedView: ConsumerStoreProductFeedView) {
    fun tryGetProductFeed(storeIdx:Long, size:Int){
        val consumerStoreProductFeedRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerStoreProductFeedRetrofitInterface::class.java)
        consumerStoreProductFeedRetrofitInterface.getProductFeed(storeIdx=storeIdx, size = size).enqueue(object :Callback<ConsumerStoreProductFeedResponse>{
            override fun onResponse(call: Call<ConsumerStoreProductFeedResponse>, response: Response<ConsumerStoreProductFeedResponse>) {
                consumerStoreProductFeedView.onGetProductFeedSuccess(response.body() as ConsumerStoreProductFeedResponse)
            }

            override fun onFailure(call: Call<ConsumerStoreProductFeedResponse>, t: Throwable) {
                consumerStoreProductFeedView.onGetProductFeedFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetProductNextFeed(storeIdx:Long, cursorIdx:Long, size:Int){
        val consumerStoreProductFeedRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerStoreProductFeedRetrofitInterface::class.java)
        consumerStoreProductFeedRetrofitInterface.getProductFeed(storeIdx=storeIdx, cursorIdx=cursorIdx, size=size).enqueue(object :Callback<ConsumerStoreProductFeedResponse>{
            override fun onResponse(call: Call<ConsumerStoreProductFeedResponse>, response: Response<ConsumerStoreProductFeedResponse>) {
                consumerStoreProductFeedView.onGetProductNextFeedSuccess(response.body() as ConsumerStoreProductFeedResponse)
            }

            override fun onFailure(call: Call<ConsumerStoreProductFeedResponse>, t: Throwable) {
                consumerStoreProductFeedView.onGetProductNextFeedFailure(t.message ?: "통신 오류")
            }

        })
    }
}