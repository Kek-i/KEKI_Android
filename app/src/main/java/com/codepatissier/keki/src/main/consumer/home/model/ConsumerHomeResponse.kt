package com.codepatissier.keki.src.main.consumer.home.model

data class ConsumerHomeResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)