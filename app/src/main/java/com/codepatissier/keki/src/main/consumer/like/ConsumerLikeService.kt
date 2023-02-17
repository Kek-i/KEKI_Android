package com.codepatissier.keki.src.main.consumer.like

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.like.model.ConsumerLikeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerLikeService(val consumerLikeView: ConsumerLikeView) {
    fun tryGetLike(size: Int){
        val consumerLikeRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerLikeRetrofitInterface::class.java)
        consumerLikeRetrofitInterface.getLike(size = size).enqueue(object : Callback<ConsumerLikeResponse>{
            override fun onResponse(
                call: Call<ConsumerLikeResponse>,
                response: Response<ConsumerLikeResponse>
            ) {
                consumerLikeView.onGetLikeSuccess(response.body() as ConsumerLikeResponse)
            }

            override fun onFailure(call: Call<ConsumerLikeResponse>, t: Throwable) {
                consumerLikeView.onGetLikeFailure(t.message ?: "통식오류")
            }
        })
    }

    fun tryGetLikeNext(cursorDate:String, size:Int){
        val consumerLikeRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerLikeRetrofitInterface::class.java)
        consumerLikeRetrofitInterface.getLike(cursorDate = cursorDate, size= size).enqueue(object : Callback<ConsumerLikeResponse>{
            override fun onResponse(
                call: Call<ConsumerLikeResponse>,
                response: Response<ConsumerLikeResponse>
            ) {
                consumerLikeView.onGetNextLikeSuccess(response.body() as ConsumerLikeResponse)
            }

            override fun onFailure(call: Call<ConsumerLikeResponse>, t: Throwable) {
                consumerLikeView.onGetNextLikeFailure(t.message ?: "통식오류")
            }
        })
    }
}