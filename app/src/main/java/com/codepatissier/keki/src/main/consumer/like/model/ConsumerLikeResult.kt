package com.codepatissier.keki.src.main.consumer.like.model

data class ConsumerLikeResult(
    val feeds : List<ConsumerLikeFeeds>,
    val cursorDate : String,
    val hasNext : Boolean,
    val numOfRows : Int
)