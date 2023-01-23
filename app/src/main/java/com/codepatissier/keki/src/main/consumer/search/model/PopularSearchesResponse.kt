package com.codepatissier.keki.src.main.consumer.search.model

data class PopularSearchesResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
)