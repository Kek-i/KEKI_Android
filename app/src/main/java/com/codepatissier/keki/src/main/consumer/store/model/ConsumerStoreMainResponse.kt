package com.codepatissier.keki.src.main.consumer.store.model

data class ConsumerStoreMainResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)
