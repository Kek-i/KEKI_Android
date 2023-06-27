package com.codepatissier.keki.src.main.consumer.store.order

import com.codepatissier.keki.src.main.consumer.store.order.model.ConsumerGetOrderScreenResponse

interface ConsumerOrderView {
    fun onGetConsumerOrderScreenSuccess(response: ConsumerGetOrderScreenResponse)
    fun onGetConsumerOrderScreenFailure(message: String)
}