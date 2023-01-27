package com.codepatissier.keki.src.main.consumer.calendar.calendaradd

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendaradd.model.PostCalendarRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerCalendarAddService(val consumerCalendarAddView: ConsumerCalendarAddView) {

    fun tryPostCalendar(postCalendarRequest: PostCalendarRequest) {
        val consumerCalendarAddRetrofitInterface = ApplicationClass.sRetrofit.create(
            ConsumerCalendarAddRetrofitInterface::class.java)
        consumerCalendarAddRetrofitInterface.postCalendar(postCalendarRequest).enqueue(object:
            Callback<BaseResponse> {
            override fun onResponse( // 에러코드를 알고 싶으면 여기서 넣으면 됨
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                if (response.body()!!.isSuccess) {
                    consumerCalendarAddView.onPostCalendarSuccess(response.body() as BaseResponse)
                }
                else if (response.errorBody() != null) {
                    val errorBody = response.errorBody()!!.string()
                    consumerCalendarAddView.onPostCalendarFailure(errorBody)
                }
                else {
                    consumerCalendarAddView.onPostCalendarFailure(response.body()?.message.toString())
                    // 추후 수정 사항: response message 말고 에러코드로 전달해서(오버로딩) 각 코드에 맞는 경고문을 ui에 띄우기(토스트 메시지 말고)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                consumerCalendarAddView.onPostCalendarFailure(t.message ?: "통신 오류")
            }
        })
    }
}