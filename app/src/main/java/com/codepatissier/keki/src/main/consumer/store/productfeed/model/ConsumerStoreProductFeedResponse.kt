package com.codepatissier.keki.src.main.consumer.store.productfeed.model

import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResult

data class ConsumerStoreProductFeedResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    var result: ProductFeedResult
)
