package com.codepatissier.keki.src.main.consumer.store.order.list.model

data class Result(
    val allOrderHistory: Int,
    val orderHistory: List<OrderHistory>,
    val orderWaiting: Int,
    val pickupWaiting: Int,
    val progressing: Int
)