package com.codepatissier.keki.util.recycler.storefeed

data class StoreFeedData(
    val postIdx: Int,
    val dessertName: String,
    val description: String,
    val postImgUrls: List<String>,
    val tags: List<String>,
    val brandName: String,
    val storeProfileImg: String,
    val like: Boolean,
    val cursorIdx: Int,
    val hasNext: Boolean
)
