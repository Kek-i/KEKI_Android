package com.codepatissier.keki.src.main.seller.mypage.orderInformation

import com.codepatissier.keki.src.main.seller.mypage.orderInformation.model.SellerOrderInformationResponse

interface SellerOrderInformationView {
    fun onGetSellerOrderInformationSuccess(response: SellerOrderInformationResponse)
    fun onGetSellerOrderInformationFailure(message: String)
}