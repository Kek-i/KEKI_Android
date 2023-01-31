package com.codepatissier.keki.src.main.consumer.calendar.calendaradd

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendaradd.model.ConsumerCalendarTagListResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendaradd.model.PostCalendarRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ConsumerCalendarAddRetrofitInterface {
    @POST("/calendars")
    fun postCalendar(
        @Body params: PostCalendarRequest
    ): Call<BaseResponse>

    @GET("/calendars/categories")
    fun getCalendarTag(): Call<ConsumerCalendarTagListResponse>
}