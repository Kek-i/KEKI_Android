package com.codepatissier.keki.src.main.consumer.calendar.calendarmodify

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.model.ConsumerCalendarModifyViewResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.model.UpdateCalendarRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerCalendarModifyService(val consumerCalendarModifyView: ConsumerCalendarModifyView) {

    fun tryUpdateCalendar(calendarIdx: Long, updateCalendarRequest: UpdateCalendarRequest) {
        val consumerCalendarModifyRetrofitInterface = ApplicationClass.sRetrofit.create(
            ConsumerCalendarModifyRetrofitInterface::class.java)
        consumerCalendarModifyRetrofitInterface.updateCalendar(calendarIdx, updateCalendarRequest).enqueue(object:
            Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.body()!!.isSuccess) {
                    consumerCalendarModifyView.onUpdateCalendarSuccess(response.body() as BaseResponse)
                }
                else if (response.errorBody() != null) {
                    val errorBody = response.errorBody()!!.string()
                    consumerCalendarModifyView.onUpdateCalendarFailure(errorBody)
                }
                else {
                    consumerCalendarModifyView.onUpdateCalendarFailure(response.body()?.message.toString())
                    // 추후 수정 사항: response message 말고 에러코드로 전달해서(오버로딩) 각 코드에 맞는 경고문을 ui에 띄우기(토스트 메시지 말고)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                consumerCalendarModifyView.onUpdateCalendarFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetCalendarModifyView(calendarIdx: Long) {
        val consumerCalendarModifyRetrofitInterface = ApplicationClass.sRetrofit.create(
            ConsumerCalendarModifyRetrofitInterface::class.java)
        consumerCalendarModifyRetrofitInterface.getCalendarModifyView(calendarIdx).enqueue(object:
            Callback<ConsumerCalendarModifyViewResponse> {
            override fun onResponse(
                call: Call<ConsumerCalendarModifyViewResponse>,
                response: Response<ConsumerCalendarModifyViewResponse>
            ) {
                consumerCalendarModifyView.onGetCalendarModifyViewSuccess(response.body() as ConsumerCalendarModifyViewResponse)
            }

            override fun onFailure(call: Call<ConsumerCalendarModifyViewResponse>, t: Throwable) {
                consumerCalendarModifyView.onGetCalendarModifyViewFailure(t.message ?: "통신 오류")
            }
        })
    }
}