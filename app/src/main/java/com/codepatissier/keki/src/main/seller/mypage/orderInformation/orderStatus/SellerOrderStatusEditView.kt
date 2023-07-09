package com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus

import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.model.SellerOrderStatusEditResponse

interface SellerOrderStatusEditView {
    fun onPatchOrderStatusSuccess(response: SellerOrderStatusEditResponse)
    fun onPatchOrderStatusFailure(message:String)
}