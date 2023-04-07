package com.codepatissier.keki.src.main.consumer.store.order.confirm.model

data class ConsumerViewOrderResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)