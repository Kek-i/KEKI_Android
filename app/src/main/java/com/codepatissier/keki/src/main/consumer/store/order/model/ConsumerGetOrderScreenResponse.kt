package com.codepatissier.keki.src.main.consumer.store.order.model

data class ConsumerGetOrderScreenResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)