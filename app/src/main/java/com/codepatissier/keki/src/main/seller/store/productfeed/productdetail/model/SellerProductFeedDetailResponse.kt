package com.codepatissier.keki.src.main.seller.store.productfeed.productdetail.model

data class SellerProductFeedDetailResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result : SellerProductFeedDetailResult
)
