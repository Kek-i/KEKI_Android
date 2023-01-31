package com.codepatissier.keki.src.main.consumer.home.model

data class Result(
    val calendarDate: Int,
    val calendarTitle: String?,
    val homeTagResList: List<HomeTagRes>,
    val nickname: String?,
    val userIdx: Int?
)