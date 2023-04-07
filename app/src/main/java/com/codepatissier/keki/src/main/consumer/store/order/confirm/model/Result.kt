package com.codepatissier.keki.src.main.consumer.store.order.confirm.model

data class Result(
    val accountHolder: String,
    val accountNumber: String,
    val getStoreDessertsAndOptions: List<GetStoreDessertsAndOption>,
    val storeIdx: Int,
    val storeName: String
)