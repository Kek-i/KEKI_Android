package com.codepatissier.keki.src.main.consumer.search.model

data class Feeds (
    val postIdx : Int,
    val dessertName : String,
    val dessertPrice : Int,
    val description : String,
    val postImgUrls : List<String>,
    val tags : List<String>,
    val brandName : String,
    val storeProfileImg : String,
    val like : Boolean

)
