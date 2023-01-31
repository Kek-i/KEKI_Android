package com.codepatissier.keki.src.main.consumer.store.productfeed.model

data class ProductFeedResult(
    val desserts : List<Desserts>,
    val cursorIdx : Long,
    val hasNext : Boolean
)