package com.codepatissier.keki.src.main.consumer.mypage

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.mypage.model.ConsumerMyPageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerMyPageService(val consumerMyPageView:ConsumerMyPageView) {
    fun tryGetMyPage(){
        val consumerMyPageRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerMyPageRetrofitInterface::class.java)
        consumerMyPageRetrofitInterface.getMyPage().enqueue(object : Callback<ConsumerMyPageResponse>{
            override fun onResponse(
                call: Call<ConsumerMyPageResponse>,
                response: Response<ConsumerMyPageResponse>
            ) {
                consumerMyPageView.onGetMyPageSuccess(response.body() as ConsumerMyPageResponse)
            }

            override fun onFailure(call: Call<ConsumerMyPageResponse>, t: Throwable) {
                consumerMyPageView.onGetMyPageFailure(t.message ?: "통신 오류")
            }

        })
    }
}