package com.codepatissier.keki.src.main.consumer.calendar.model

import com.codepatissier.keki.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ConsumerCalendarListResponse (
    @SerializedName("result") val result: List<ResultCalendar>
) : BaseResponse()