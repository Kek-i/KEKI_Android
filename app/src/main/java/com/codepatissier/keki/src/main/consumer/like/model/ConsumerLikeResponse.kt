package com.codepatissier.keki.src.main.consumer.like.model

data class ConsumerLikeResponse (
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    var result: ConsumerLikeResult
)