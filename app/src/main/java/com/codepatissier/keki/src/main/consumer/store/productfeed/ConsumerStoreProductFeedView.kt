package com.codepatissier.keki.src.main.consumer.store.productfeed

import com.codepatissier.keki.src.main.consumer.store.productfeed.model.ConsumerStoreProductFeedResponse

interface ConsumerStoreProductFeedView {
    // 상품 첫 피드 가져오기
    fun onGetProductFeedSuccess(response:ConsumerStoreProductFeedResponse)
    fun onGetProductFeedFailure(message:String)

    // 상품 다음 피드 가져오기
    fun onGetProductNextFeedSuccess(response:ConsumerStoreProductFeedResponse)
    fun onGetProductNextFeedFailure(message:String)
}