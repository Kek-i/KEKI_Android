package com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail.model

data class ConsumerNoticeDetailResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)