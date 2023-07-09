package com.codepatissier.keki.src.main.seller.mypage.orderInformation.model

data class SellerOrderInformationResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result:Result
)
