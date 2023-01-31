package com.codepatissier.keki.src.main.consumer.calendar.calendardetail

import com.codepatissier.keki.src.main.consumer.calendar.calendardetail.model.ConsumerCalendarDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ConsumerCalendarDetailRetrofitInterface {
    @GET("/calendars/{calendarIdx}")
    fun getCalendarDetail(
        @Path("calendarIdx") calendarIdx: Long
    ): Call<ConsumerCalendarDetailResponse>
}