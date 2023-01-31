package com.codepatissier.keki.src.main.consumer.calendar

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.calendar.model.ConsumerCalendarListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerCalendarService(val consumerCalendarView: ConsumerCalendarView) {

    fun tryGetCalendarList() {
        val consumerCalendarRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerCalendarRetrofitInterface::class.java)
        consumerCalendarRetrofitInterface.getCalendarList().enqueue(object: Callback<ConsumerCalendarListResponse> {
            override fun onResponse(call: Call<ConsumerCalendarListResponse>, response: Response<ConsumerCalendarListResponse>) {
                consumerCalendarView.onGetCalendarListSuccess(response.body() as ConsumerCalendarListResponse)
            }

            override fun onFailure(call: Call<ConsumerCalendarListResponse>, t: Throwable) {
                consumerCalendarView.onGetCalendarListFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteCalendar(calendarIdx: Long) {
        val consumerCalendarRetrofitInterface = ApplicationClass.sRetrofit.create(ConsumerCalendarRetrofitInterface::class.java)
        consumerCalendarRetrofitInterface.deleteCalendar(calendarIdx).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.body()!!.isSuccess) {
                    consumerCalendarView.onDeleteCalendarSuccess(response.body() as BaseResponse)
                }
                else if (response.errorBody() != null) {
                    val errorBody = response.errorBody()!!.string()
                    consumerCalendarView.onDeleteCalendarFailure(errorBody)
                }
                else {
                    consumerCalendarView.onDeleteCalendarFailure(response.body()?.message.toString())
                    // 추후 수정 사항: response message 말고 에러코드로 전달해서(오버로딩) 각 코드에 맞는 경고문을 ui에 띄우기(토스트 메시지 말고)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                consumerCalendarView.onDeleteCalendarFailure(t.message ?: "통신 오류")
            }
        })
    }
}