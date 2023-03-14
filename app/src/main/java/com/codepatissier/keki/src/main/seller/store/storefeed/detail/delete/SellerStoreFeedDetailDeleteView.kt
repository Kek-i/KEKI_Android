package com.codepatissier.keki.src.main.seller.store.storefeed.detail.delete

import com.codepatissier.keki.config.BaseResponse

interface SellerStoreFeedDetailDeleteView {
    fun onDeleteSellerStoreFeedDetailDeleteSuccess(response: BaseResponse)

    fun onDeleteSellerStoreFeedDetailDeleteFailure(message: String)
}