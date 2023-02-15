package com.codepatissier.keki.src.main.consumer.calendar.calendarmodify

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.model.ConsumerCalendarModifyViewResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.model.UpdateCalendarRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ConsumerCalendarModifyRetrofitInterface {
    @PATCH("/calendars/{calendarIdx}/edit")
    fun updateCalendar(
        @Path("calendarIdx") calendarIdx: Long,
        @Body params: UpdateCalendarRequest
    ): Call<BaseResponse>

    @GET("/calendars/{calendarIdx}/edit")
    fun getCalendarModifyView(
        @Path("calendarIdx") calendarIdx: Long
    ): Call<ConsumerCalendarModifyViewResponse>
}