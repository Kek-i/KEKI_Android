package com.codepatissier.keki.src.main.consumer.search.searchresult.model

data class SearchResult(
    val feeds: List<Feeds>,
    val cursorIdx: Long,
    val cursorPrice: Int,
    val cursorPopulaNum: Int,
    val hasNext: Boolean,
    val numOfRows: Int
)