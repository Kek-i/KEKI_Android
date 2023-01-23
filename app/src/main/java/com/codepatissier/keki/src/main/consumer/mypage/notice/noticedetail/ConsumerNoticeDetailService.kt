package com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail.model.ConsumerNoticeDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerNoticeDetailService(val consumerNoticeDetailView: ConsumerNoticeDetailView) {

    fun tryGetNoticeDetail(noticeIdx: Int){
        val consumerNoticeDetailRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerNoticeDetailRetrofitInterface::class.java)
        consumerNoticeDetailRetrofitInterface.getNoticeDetail(noticeIdx).enqueue(object: Callback<ConsumerNoticeDetailResponse>{
            override fun onResponse(
                call: Call<ConsumerNoticeDetailResponse>,
                response: Response<ConsumerNoticeDetailResponse>
            ) {
                consumerNoticeDetailView.onGetNoticeDetailSuccess(response.body() as ConsumerNoticeDetailResponse)
            }

            override fun onFailure(call: Call<ConsumerNoticeDetailResponse>, t: Throwable) {
                consumerNoticeDetailView.onGetNoticeDetailFailure(t.message ?: "통신 오류")
            }

        })
    }
}