package com.codepatissier.keki.src.main.seller.store.storefeed.storeadd

import com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model.SellerFeedAddViewResponse

interface SellerStoreFeedAddView {
    fun onGetFeedAddViewSuccess(response: SellerFeedAddViewResponse)
    fun onGetFeedAddViewFailure(message: String)
}