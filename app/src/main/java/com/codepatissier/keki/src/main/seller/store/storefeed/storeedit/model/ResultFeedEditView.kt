package com.codepatissier.keki.src.main.seller.store.storefeed.storeedit.model

import com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model.DessertName

data class ResultFeedEditView (
    val postIdx: Long,
    val currentDessertIdx: Long,
    val currentDessertName: String,
    val description: String,
    val postImgUrls: List<String>,
    val currentTags: List<String>,
    val desserts: List<DessertName>,
    val tagCategories: List<String>
)
