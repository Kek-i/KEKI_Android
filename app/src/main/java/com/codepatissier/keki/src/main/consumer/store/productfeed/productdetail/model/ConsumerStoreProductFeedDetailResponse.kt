package com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail.model

data class ConsumerStoreProductFeedDetailResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    var result: ProductFeedDetailResult
)
