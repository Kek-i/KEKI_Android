package com.codepatissier.keki.src.main.consumer.calendar.calendaradd.model

data class PostCalendarRequest(
    val kindOfCalendar: String,
    val title: String,
    val date: String,
    val hashTags: List<Map<String, String>>
)
