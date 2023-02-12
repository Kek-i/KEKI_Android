package com.codepatissier.keki.src.main.seller.store.productfeed.model


data class SellerProductFeedDetailResult(
    val nickname: String,
    val dessertName : String,
    val dessertPrice: Int,
    val dessertDescription: String,
    val images : List<Image>
)