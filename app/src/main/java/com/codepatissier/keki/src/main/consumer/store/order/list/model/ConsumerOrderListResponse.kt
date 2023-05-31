package com.codepatissier.keki.src.main.consumer.store.order.list.model

data class ConsumerOrderListResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)