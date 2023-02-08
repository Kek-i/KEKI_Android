package com.codepatissier.keki.src.main.consumer.search.searchresult.model

data class Feeds (
    val postIdx : Long,
    val dessertName : String,
    val dessertPrice : Int,
    val description : String,
    val postImgUrls : List<String>,
    val tags : List<String>,
    val storeName : String,
    val storeProfileImg : String,
    val like : Boolean

)
