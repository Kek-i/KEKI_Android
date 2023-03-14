package com.codepatissier.keki.src.main.seller.store.productfeed.productdetail

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.seller.store.productfeed.productdetail.model.SellerProductFeedDetailResponse

interface SellerProductFeedDetailView {
    // 상품 첫 피드 가져오기
    fun onGetProductFeedSuccess(response: SellerProductFeedDetailResponse)
    fun onGetProductFeedFailure(message:String)

    fun onPatchProductFeedSuccess(response: BaseResponse)
    fun onPatchProductFeedFailure(message:String)

    fun onDelProductFeedSuccess(response: BaseResponse)
    fun onDelProductFeedFailure(message:String)
}

