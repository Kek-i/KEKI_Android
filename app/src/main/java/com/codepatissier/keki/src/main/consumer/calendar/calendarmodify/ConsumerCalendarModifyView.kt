package com.codepatissier.keki.src.main.consumer.calendar.calendarmodify

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.model.ConsumerCalendarModifyViewResponse

interface ConsumerCalendarModifyView {
    fun onUpdateCalendarSuccess(response: BaseResponse)
    fun onUpdateCalendarFailure(message: String)

    fun onGetCalendarModifyViewSuccess(response: ConsumerCalendarModifyViewResponse)
    fun onGetCalendarModifyViewFailure(message: String)
}