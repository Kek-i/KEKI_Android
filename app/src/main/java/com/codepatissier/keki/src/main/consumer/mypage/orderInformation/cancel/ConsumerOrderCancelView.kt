package com.codepatissier.keki.src.main.consumer.mypage.orderInformation.cancel

import com.codepatissier.keki.src.main.consumer.mypage.orderInformation.cancel.model.ConsumerOrderCancelResponse

interface ConsumerOrderCancelView {
    fun onGetConsumerOrderCancelSuccess(response: ConsumerOrderCancelResponse)
    fun onGetConsumerOrderCancelFailure(message: String)
}