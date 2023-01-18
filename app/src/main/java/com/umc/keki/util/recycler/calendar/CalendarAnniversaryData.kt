package com.umc.keki.util.recycler.calendar

data class CalendarAnniversaryData(
    var title: String,
    var date: String,
    var dday: String,
    var type: String,
    var firstTag: String?,
    var secondTag: String?,
    var thirdTag: String?
)