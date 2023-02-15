package com.codepatissier.keki.src.main.consumer.store.storefeed

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerStoreFeedDetailService(val consumerStoreFeedDetailView: ConsumerStoreFeedDetailView) {

    fun tryGetConsumerStoreFeedDetailRetrofitInterface(storeIdx: Long, cursorIdx: Int?, size: Int?){
        val consumerStoreFeedDetailInterface = ApplicationClass.sRetrofit.create(ConsumerStoreFeedDetailRetrofitInterface::class.java)

        consumerStoreFeedDetailInterface.getConsumerStoreFeedDetail(storeIdx, cursorIdx, size)
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

    fun tryPostConsumerStoreFeedDetailLike(postIdx: Long){
        val consumerStoreFeedDetailLikeInterface = ApplicationClass.sRetrofit.create(ConsumerStoreFeedDetailRetrofitInterface::class.java)

        consumerStoreFeedDetailLikeInterface.postConsumerStoreFeedDetailLike(postIdx).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                consumerStoreFeedDetailView.onPostConsumerStoreFeedDetailLikeSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                consumerStoreFeedDetailView.onPostConsumerStoreFeedDetailLikeFailure(t.message ?: "통신 오류")
            }

        })
    }



}