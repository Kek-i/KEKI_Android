package com.codepatissier.keki.src.main.seller.store.storefeed.storeedit

import com.codepatissier.keki.src.main.seller.store.storefeed.storeedit.model.SellerFeedEditViewResponse

interface SellerStoreFeedEditView {
    fun onGetFeedEditViewSuccess(response: SellerFeedEditViewResponse)
    fun onGetFeedEditViewFailure(message: String)
}