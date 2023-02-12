package com.codepatissier.keki.src.main.consumer.calendar.calendarmodify

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendaradd.model.ConsumerCalendarTagListResponse
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

    @GET("/calendars/categories")
    fun getCalendarTag(): Call<ConsumerCalendarTagListResponse>
}