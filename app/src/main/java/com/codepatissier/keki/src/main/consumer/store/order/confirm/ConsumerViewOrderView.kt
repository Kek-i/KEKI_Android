package com.codepatissier.keki.src.main.consumer.store.order.confirm

import com.codepatissier.keki.src.main.consumer.store.order.confirm.model.ConsumerViewOrderResponse

interface ConsumerViewOrderView {
    fun onGetConsumerViewOrderSuccess(response: ConsumerViewOrderResponse)

    fun onGetConsumerViewOrderFailure(message: String)
}