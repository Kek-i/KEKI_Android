package com.codepatissier.keki.src.main.seller.store.productfeed

import com.codepatissier.keki.src.main.seller.store.productfeed.model.SellerProductFeedDetailResponse

interface SellerProductFeedDetailView {
    // 상품 첫 피드 가져오기
    fun onGetProductFeedSuccess(response: SellerProductFeedDetailResponse)
    fun onGetProductFeedFailure(message:String)

}