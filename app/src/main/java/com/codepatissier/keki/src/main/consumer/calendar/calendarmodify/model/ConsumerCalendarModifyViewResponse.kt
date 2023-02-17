package com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.model

import com.codepatissier.keki.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ConsumerCalendarModifyViewResponse (
    @SerializedName("result") val result: ResultCalendarModifyView
) : BaseResponse()
