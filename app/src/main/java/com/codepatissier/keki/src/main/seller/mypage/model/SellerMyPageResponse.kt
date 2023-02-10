package com.codepatissier.keki.src.main.seller.mypage.model


data class SellerMyPageResponse (
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: MyPageResult
)