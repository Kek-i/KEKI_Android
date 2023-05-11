package com.codepatissier.keki.src.main.seller.mypage.orderlist.model

data class SellerOrderListResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)