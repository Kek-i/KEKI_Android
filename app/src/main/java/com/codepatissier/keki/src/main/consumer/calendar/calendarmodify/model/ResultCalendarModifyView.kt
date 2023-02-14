package com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.model

data class ResultCalendarModifyView (
    val kindOfCalendar: String,
    val title: String,
    val date: String,
    val calDate: String,
    val hashTags: List<Map<String, String>>
)
