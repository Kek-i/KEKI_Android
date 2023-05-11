package com.codepatissier.keki.util.recycler.order

data class SellerOrderListData(
    var orderIdx: Int,
    var userName: String,
    var userProfileImg: String?,
    var totalPrice: Int,
    var dessertName: String,
    var pickUpDate: String
)
