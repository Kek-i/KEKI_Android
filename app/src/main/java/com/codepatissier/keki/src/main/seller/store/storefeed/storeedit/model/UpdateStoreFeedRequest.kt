package com.codepatissier.keki.src.main.seller.store.storefeed.storeedit.model

data class UpdateStoreFeedRequest (
        val dessertIdx: Long,
        val description: String,
        val postImgUrls: List<String>,
        val tags: List<String>
)
