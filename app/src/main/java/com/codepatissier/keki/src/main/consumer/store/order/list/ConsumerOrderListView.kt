package com.codepatissier.keki.src.main.consumer.store.order.list;

import com.codepatissier.keki.src.main.consumer.store.order.list.model.ConsumerOrderListResponse

interface ConsumerOrderListView {
    fun onGetConsumerOrderListSuccess(response: ConsumerOrderListResponse)
    fun onGetConsumerOrderListFailure(message: String)
}
