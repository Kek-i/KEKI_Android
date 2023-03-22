package com.codepatissier.keki.src.main.seller.store.storefeed.storeadd

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model.SellerFeedAddViewResponse

interface SellerStoreFeedAddView {
    fun onGetFeedAddViewSuccess(response: SellerFeedAddViewResponse)
    fun onGetFeedAddViewFailure(message: String)

    fun onPostStoreFeedSuccess(response: BaseResponse)
    fun onPostStoreFeedFailure(message: String)
}