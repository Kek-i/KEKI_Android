package com.codepatissier.keki.src.main.consumer.mypage.orderInformation.model

data class ConsumerOrderInformationResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result:Result
)
