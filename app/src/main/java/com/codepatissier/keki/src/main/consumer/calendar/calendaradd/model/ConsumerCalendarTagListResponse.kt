package com.codepatissier.keki.src.main.consumer.calendar.calendaradd.model

import com.codepatissier.keki.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ConsumerCalendarTagListResponse(
    @SerializedName("result") val result: List<ResultCalendarTag>
) : BaseResponse()
