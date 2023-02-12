package com.codepatissier.keki.src.main.consumer.calendar.calendardetail.model

data class ResultCalendarDetail (
    val kindOfCalendar: String,
    val title: String,
    val date: String,
    val calDate: String,
    val hashTags: List<Map<String, String>>
) : java.io.Serializable