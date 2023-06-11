package com.codepatissier.keki.src.main.seller.mypage.orderInformation.model

import java.util.Date

data class Result(
    val orderStatus:String,
    val dessertName:String,
    val dessertPrice:Int,
    val optionPrice:Int,
    val totalPrice:Int,
    val request:String,
    val pickupDate:Date,
    val userInfo:UserInfo,
    val orderImgs:OrderImgs,
    val optionOrders:OptionOrders
)
