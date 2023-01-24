package com.codepatissier.keki.src.main.consumer.calendar.calendardetail

import com.codepatissier.keki.src.main.consumer.calendar.calendardetail.model.ConsumerCalendarDetailResponse

interface ConsumerCalendarDetailView {
    fun onGetCalendarDetailSuccess(response: ConsumerCalendarDetailResponse)
    fun onGetCalendarDetailFailure(message: String)
}