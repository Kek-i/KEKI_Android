package com.codepatissier.keki.src.main.seller.mypage.orderlist.model

data class OrderHistory(
    val dessertName: String,
    val orderIdx: Int,
    val pickupDate: String,
    val totalPrice: Int,
    val userName: String,
    val userProfileImg: String?
)