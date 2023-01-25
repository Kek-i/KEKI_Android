package com.codepatissier.keki.src.main.consumer.calendar

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.calendar.model.ConsumerCalendarListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerCalendarService(val consumerCalendarView: ConsumerCalendarView) {

    fun tryGetCalendarList() {
        val consumerCalendarRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerCalendarRetrofitInterface::class.java)
        consumerCalendarRetrofitInterface.getCalendarList().enqueue(object: Callback<ConsumerCalendarListResponse> {
            override fun onResponse(
                call: Call<ConsumerCalendarListResponse>,
                response: Response<ConsumerCalendarListResponse>
            ) {
                consumerCalendarView.onGetCalendarListSuccess(response.body() as ConsumerCalendarListResponse)
            }

            override fun onFailure(call: Call<ConsumerCalendarListResponse>, t: Throwable) {
                consumerCalendarView.onGetCalendarListFailure(t.message ?: "통신 오류")
            }
        })
    }
}