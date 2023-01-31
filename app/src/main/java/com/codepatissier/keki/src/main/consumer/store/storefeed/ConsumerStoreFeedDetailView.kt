package com.codepatissier.keki.src.main.consumer.store.storefeed

import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse

interface ConsumerStoreFeedDetailView {
    fun onGetConsumerStoreFeedDetailSuccess(response: ConsumerStoreDetailFeedResponse)

    fun onGetConsumerStoreFeedDetailFailure(message: String)
}