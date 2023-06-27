package com.codepatissier.keki.src.main.consumer.store.order.model

data class Result(
    val accountHolder: String,
    val accountNumber: String,
    val getStoreDessertsAndOptions: List<GetStoreDessertsAndOption>,
    val storeIdx: Int,
    val storeImgUrl: String,
    val storeName: String
)