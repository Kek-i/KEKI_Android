package com.codepatissier.keki.src.main.consumer.calendar

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.calendar.model.ConsumerCalendarListResponse

interface ConsumerCalendarView {
    fun onGetCalendarListSuccess(response: ConsumerCalendarListResponse)
    fun onGetCalendarListFailure(message: String)

    fun onDeleteCalendarSuccess(response: BaseResponse)
    fun onDeleteCalendarFailure(message: String)
}