package com.codepatissier.keki.src.main.consumer.home.onefeed.model

data class ConsumerOneFeedDetailResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)