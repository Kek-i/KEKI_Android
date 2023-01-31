package com.codepatissier.keki.src.main.consumer.mypage.model


data class ConsumerMyPageResponse (
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: MyPageResult
)