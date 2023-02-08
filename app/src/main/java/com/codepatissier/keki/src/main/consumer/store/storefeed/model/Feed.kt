package com.codepatissier.keki.src.main.consumer.store.storefeed.model

data class Feed(
    val storeName: String,
    val description: String,
    val dessertName: String,
    val like: Boolean,
    val postIdx: Long,
    val postImgUrls: List<String>,
    val storeProfileImg: String,
    val tags: List<String>
)