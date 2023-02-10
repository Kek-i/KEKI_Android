package com.codepatissier.keki.src.main.seller.mypage.profileEdit.model

data class SellerProfileEditBody(
    val storeImgUrl : String? = null,
    val nickname : String,
    val address: String,
    val introduction: String,
    val orderUrl: String
)
