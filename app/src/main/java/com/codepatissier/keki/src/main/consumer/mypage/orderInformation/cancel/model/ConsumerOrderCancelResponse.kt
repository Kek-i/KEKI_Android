package com.codepatissier.keki.src.main.consumer.mypage.orderInformation.cancel.model

data class ConsumerOrderCancelResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String
)
