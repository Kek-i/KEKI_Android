package com.codepatissier.keki.src.main.consumer.mypage.notice.noticeall.model

data class ConsumerNoticeResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
)