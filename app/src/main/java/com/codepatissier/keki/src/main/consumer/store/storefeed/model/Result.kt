package com.codepatissier.keki.src.main.consumer.store.storefeed.model

data class Result(
    val cursorIdx: Int,
    val feeds: List<Feed>,
    val hasNext: Boolean,
    val numOfRows: Int
)