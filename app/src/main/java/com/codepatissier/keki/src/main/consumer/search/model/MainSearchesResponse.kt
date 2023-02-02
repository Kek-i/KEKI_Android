package com.codepatissier.keki.src.main.consumer.search.model

data class MainSearchesResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    var result: Result
)