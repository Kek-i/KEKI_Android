package com.codepatissier.keki.src.main.seller.store.storefeed.detail

import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse

interface SellerStoreFeedDetailView {
    fun onGetSellerStoreFeedDetailSuccess(response: ConsumerStoreDetailFeedResponse)

    fun onGetSellerStoreFeedDetailFailure(message: String)
}