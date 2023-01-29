package com.codepatissier.keki.src.main.consumer.calendar

import com.codepatissier.keki.src.main.consumer.calendar.model.ConsumerCalendarListResponse
import retrofit2.Call
import retrofit2.http.GET

interface ConsumerCalendarRetrofitInterface {
    @GET("/calendars")
    fun getCalendarList(): Call<ConsumerCalendarListResponse>
}
