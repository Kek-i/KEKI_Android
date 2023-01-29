package com.codepatissier.keki.src.main.consumer.calendar

import com.codepatissier.keki.src.main.consumer.calendar.model.ConsumerCalendarListResponse

interface ConsumerCalendarView {
    fun onGetCalendarListSuccess(response: ConsumerCalendarListResponse)
    fun onGetCalendarListFailure(message: String)
}