package com.codepatissier.keki.util.recycler.storefeed

data class StoreFeedData(
    val postIdx: Long,
    val dessertName: String,
    val description: String,
    val postImgUrls: List<String>,
    val tags: List<String>,
    val storeName: String,
    val storeProfileImg: String?,
    val like: Boolean,
    val cursorIdx: Int,
    val hasNext: Boolean
)
