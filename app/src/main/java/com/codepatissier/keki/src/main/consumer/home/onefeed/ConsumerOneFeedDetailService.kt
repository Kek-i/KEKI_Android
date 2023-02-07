package com.codepatissier.keki.src.main.consumer.home.onefeed

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.home.onefeed.model.ConsumerOneFeedDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class ConsumerOneFeedDetailService(val consumerOneFeedDetailView: ConsumerOneFeedDetailView) {
    fun tryGetConsumerOneFeedDetail(postIdx: Int){
        val consumerOneFeedDetailRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerOneFeedDetailRetrofitInterface::class.java)
        consumerOneFeedDetailRetrofitInterface.getConsumerOneFeedDetail(postIdx).enqueue(object: Callback<ConsumerOneFeedDetailResponse>{
            override fun onResponse(
                call: Call<ConsumerOneFeedDetailResponse>,
                response: Response<ConsumerOneFeedDetailResponse>
            ) {
                consumerOneFeedDetailView.onGetConsumerOneFeedDetailSuccess(response.body() as ConsumerOneFeedDetailResponse)
            }

            override fun onFailure(call: Call<ConsumerOneFeedDetailResponse>, t: Throwable) {
                consumerOneFeedDetailView.onGetConsumerOneFeedDetailFailure(t.message ?: "통신 오류")
            }

        })
    }
}