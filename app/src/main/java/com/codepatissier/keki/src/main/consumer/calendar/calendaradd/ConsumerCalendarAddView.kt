package com.codepatissier.keki.src.main.consumer.calendar.calendaradd

import com.codepatissier.keki.config.BaseResponse

interface ConsumerCalendarAddView {
    fun onPostCalendarSuccess(response: BaseResponse)
    fun onPostCalendarFailure(message: String)
}