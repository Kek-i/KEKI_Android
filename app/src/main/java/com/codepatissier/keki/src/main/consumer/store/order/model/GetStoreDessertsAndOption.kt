package com.codepatissier.keki.src.main.consumer.store.order.model

data class GetStoreDessertsAndOption(
    val dessertIdx: Int,
    val dessertName: String,
    val dessertPrice: Int,
    val options: List<Option>
)