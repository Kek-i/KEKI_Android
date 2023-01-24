package com.codepatissier.keki.src.main.consumer.search.model

data class SearchResult(
    val feeds: List<Feeds>,
    val cursorIdx: Long,
    val hasNext: Boolean,
    val numOfNums: Int
)