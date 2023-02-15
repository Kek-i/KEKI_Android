package com.codepatissier.keki.src.main.consumer.home.onefeed.model

data class Result(
    val description: String,
    val dessertName: String,
    val dessertPrice: Int,
    val like: Boolean,
    val postIdx: Int,
    val postImgUrls: List<String>,
    val storeIdx: Long,
    val storeName: String,
    val storeProfileImg: String,
    val tags: List<String>
)