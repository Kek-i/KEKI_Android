package com.codepatissier.keki.src.main.consumer.home.onefeed

import com.codepatissier.keki.src.main.consumer.home.onefeed.model.ConsumerOneFeedDetailResponse

interface ConsumerOneFeedDetailView {
    fun onGetConsumerOneFeedDetailSuccess(response: ConsumerOneFeedDetailResponse)

    fun onGetConsumerOneFeedDetailFailure(message: String)
}