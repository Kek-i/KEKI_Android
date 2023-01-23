package com.codepatissier.keki.src.main.consumer.mypage.notice

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.mypage.notice.model.ConsumerNoticeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerNoticeService(val consumerNoticeView: ConsumerNoticeView) {

    fun tryGetNotice(){
        val consumerNoticeRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerNoticeRetrofitInterface::class.java)
        consumerNoticeRetrofitInterface.getNotice().enqueue(object: Callback<ConsumerNoticeResponse>{
            override fun onResponse(
                call: Call<ConsumerNoticeResponse>,
                response: Response<ConsumerNoticeResponse>
            ) {
                consumerNoticeView.onGetNoticeSuccess(response.body() as ConsumerNoticeResponse)
            }

            override fun onFailure(call: Call<ConsumerNoticeResponse>, t: Throwable) {
                consumerNoticeView.onGetNoticeFailure(t.message ?: "통신 오류")
            }

        })
    }
}