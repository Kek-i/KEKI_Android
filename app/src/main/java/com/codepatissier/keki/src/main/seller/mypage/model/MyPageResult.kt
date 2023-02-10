package com.codepatissier.keki.src.main.seller.mypage.model

class MyPageResult (
    val storeIdx : Long,
    val storeImgUrl : String? = null,
    val email: String,
    val nickname : String,
    val address: String,
    val introduction: String,
    val orderUrl: String,
    val businessName: String,
    val brandName: String,
    val businessAddress: String,
    val businessNumber: String
)