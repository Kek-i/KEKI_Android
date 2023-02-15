package com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.model

data class UpdateCalendarRequest(
    val kindOfCalendar: String,
    val title: String,
    val date: String,
    val hashTags: List<Map<String, String>>
)
