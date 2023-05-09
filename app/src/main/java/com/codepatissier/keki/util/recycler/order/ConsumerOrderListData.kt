package com.codepatissier.keki.util.recycler.order

// 수정 필요
data class ConsumerOrderListData(
    var orderIdx: Int,
    var userName: String,
    var userProfileImg: String?,
    var totalPrice: Int,
    var dessertName: String,
    var pickUpDate: String
)
