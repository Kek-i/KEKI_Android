package com.codepatissier.keki.src.main.consumer.calendar.calendardetail.model

import com.codepatissier.keki.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ConsumerCalendarDetailResponse (
    @SerializedName("result") val result: ResultCalendarDetail
) : BaseResponse()