package com.codepatissier.keki.src.main.consumer.calendar.calendarmodify

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendaradd.model.ConsumerCalendarTagListResponse

interface ConsumerCalendarModifyView {
    fun onUpdateCalendarSuccess(response: BaseResponse)
    fun onUpdateCalendarFailure(message: String)

    fun onGetCalendarTagSuccess(response: ConsumerCalendarTagListResponse)
    fun onGetCalendarTagFailure(message: String)
}