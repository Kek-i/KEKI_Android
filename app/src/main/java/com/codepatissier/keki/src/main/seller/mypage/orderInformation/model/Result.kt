package com.codepatissier.keki.src.main.seller.mypage.orderInformation.model

import java.util.Date

data class Result(
    val orderDate:String,
    val orderStatus:String,
    val dessertName:String,
    val dessertPrice:Int,
    val optionPrice:Int,
    val totalPrice:Int,
    val request:String,
    val pickupDate:Date,
    val userInfo:UserInfo,
    val orderImgs:ArrayList<OrderImgs>,
    val optionOrders:ArrayList<OptionOrders>
)
