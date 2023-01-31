package com.codepatissier.keki.src.main.consumer.calendar

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.calendar.model.ConsumerCalendarListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ConsumerCalendarRetrofitInterface {
    @GET("/calendars")
    fun getCalendarList(): Call<ConsumerCalendarListResponse>

    @PATCH("/calendars/{calendarIdx}")
    fun deleteCalendar(
        @Path("calendarIdx") calendarIdx: Long
    ): Call<BaseResponse>
}
