package com.codepatissier.keki.src.main.consumer.search.model

data class RecentSeenFeedResult (
    val postIdx: Long,
    val dessertName: String,
    val dessertPrice:Int,
    val description: String,
    val postImgUrls:List<String>,
    val tags: List<String>,
    val storeName: String,
    val storeProfileImg: String,
    val storeIdx:Long,
    val like: Boolean
        )