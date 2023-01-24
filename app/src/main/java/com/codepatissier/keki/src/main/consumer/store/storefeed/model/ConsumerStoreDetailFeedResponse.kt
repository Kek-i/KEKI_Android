package com.codepatissier.keki.src.main.consumer.store.storefeed.model

data class ConsumerStoreDetailFeedResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)