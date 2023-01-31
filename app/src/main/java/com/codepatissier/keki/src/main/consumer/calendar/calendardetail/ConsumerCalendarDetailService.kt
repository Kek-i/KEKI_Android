package com.codepatissier.keki.src.main.consumer.calendar.calendardetail

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.calendar.calendardetail.model.ConsumerCalendarDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerCalendarDetailService(val consumerCalendarDetailView: ConsumerCalendarDetailView) {

    fun tryGetCalendarDetail(calendarIdx: Long) {
        val consumerCalendarDetailRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerCalendarDetailRetrofitInterface::class.java)
        consumerCalendarDetailRetrofitInterface.getCalendarDetail(calendarIdx).enqueue(object: Callback<ConsumerCalendarDetailResponse> {
            override fun onResponse(
                call: Call<ConsumerCalendarDetailResponse>,
                response: Response<ConsumerCalendarDetailResponse>
            ) {
                consumerCalendarDetailView.onGetCalendarDetailSuccess(response.body() as ConsumerCalendarDetailResponse)
            }

            override fun onFailure(call: Call<ConsumerCalendarDetailResponse>, t: Throwable) {
                consumerCalendarDetailView.onGetCalendarDetailFailure(t.message ?: "통신 오류")
            }
        })
    }
}