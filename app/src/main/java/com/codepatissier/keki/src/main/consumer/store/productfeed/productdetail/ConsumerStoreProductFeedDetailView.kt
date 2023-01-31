package com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail

import com.codepatissier.keki.src.main.consumer.store.productfeed.model.ConsumerStoreProductFeedResponse
import com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail.model.ConsumerStoreProductFeedDetailResponse

interface ConsumerStoreProductFeedDetailView {
    // 상품 첫 피드 가져오기
    fun onGetProductFeedSuccess(response: ConsumerStoreProductFeedDetailResponse)
    fun onGetProductFeedFailure(message:String)

}