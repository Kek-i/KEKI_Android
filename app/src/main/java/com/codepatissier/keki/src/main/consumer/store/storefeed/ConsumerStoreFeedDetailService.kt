package com.codepatissier.keki.src.main.consumer.store.storefeed

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerStoreFeedDetailService(val consumerStoreFeedDetailView: ConsumerStoreFeedDetailView) {

    fun tryGetConsumerStoreFeedDetailRetrofitInterface(searchTag: String, cursorIdx: Int?, size: Int?){
        val consumerStoreFeedDetailInterface = ApplicationClass.sRetrofit.create(ConsumerStoreFeedDetailRetrofitInterface::class.java)

        consumerStoreFeedDetailInterface.getConsumerStoreFeedDetail(searchTag, cursorIdx, size)
            .enqueue(object: Callback<ConsumerStoreDetailFeedResponse>{
                override fun onResponse(
                    call: Call<ConsumerStoreDetailFeedResponse>,
                    response: Response<ConsumerStoreDetailFeedResponse>
                ) {
                    consumerStoreFeedDetailView.onGetConsumerStoreFeedDetailSuccess(response.body() as ConsumerStoreDetailFeedResponse)
                }

                override fun onFailure(call: Call<ConsumerStoreDetailFeedResponse>, t: Throwable) {
                    consumerStoreFeedDetailView.onGetConsumerStoreFeedDetailFailure(t.message ?: "통신 오류")
                }

            })
    }



}