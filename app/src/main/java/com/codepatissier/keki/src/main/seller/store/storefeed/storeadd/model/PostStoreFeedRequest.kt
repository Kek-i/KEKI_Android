package com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model

data class PostStoreFeedRequest (
    val dessertIdx: Long,
    val description: String,
    val postImgUrls: List<String>,
    val tags: List<String>
)