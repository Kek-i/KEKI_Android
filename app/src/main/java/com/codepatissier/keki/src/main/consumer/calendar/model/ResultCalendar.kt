package com.codepatissier.keki.src.main.consumer.calendar.model

data class ResultCalendar (
    val calendarIdx: Long,
    val kindOfCalendar: String,
    val title: String,
    val date: String,
    val calDate: String
)