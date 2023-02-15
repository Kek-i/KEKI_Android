package com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.model

data class ResultCalendarModifyView (
    val kindOfCalendar: String,
    val title: String,
    val date: String,
    val hashTags: List<Map<String, String>>,
    val addHashTags: List<Map<String, String>>
)
