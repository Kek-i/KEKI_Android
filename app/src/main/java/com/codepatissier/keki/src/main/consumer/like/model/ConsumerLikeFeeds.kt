package com.codepatissier.keki.src.main.consumer.like.model

data class ConsumerLikeFeeds(
    val postIdx : Long,
    val dessertName : String,
    val dessertPrice : Int,
    val postImgUrl : String
)