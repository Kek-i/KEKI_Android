package com.codepatissier.keki.src.main.consumer.store.info.model


data class ConsumerStoreInfoResponse (
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
    )