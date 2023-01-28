package com.codepatissier.keki.src.main.consumer.calendar.calendaradd

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendaradd.model.ConsumerCalendarTagListResponse

interface ConsumerCalendarAddView {
    fun onPostCalendarSuccess(response: BaseResponse)
    fun onPostCalendarFailure(message: String)

    fun onGetCalendarTagSuccess(response: ConsumerCalendarTagListResponse)
    fun onGetCalendarTagFailure(message: String)
}