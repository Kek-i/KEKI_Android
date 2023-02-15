package com.codepatissier.keki.src.main.consumer.mypage.notice.model

data class ConsumerNoticeResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<NoticeResult>
)