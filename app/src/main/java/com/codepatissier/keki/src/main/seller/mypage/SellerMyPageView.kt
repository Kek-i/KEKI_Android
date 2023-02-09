package com.codepatissier.keki.src.main.seller.mypage

import com.codepatissier.keki.src.main.seller.mypage.model.SellerMyPageResponse


interface SellerMyPageView {
    fun onGetMyPageSuccess(response: SellerMyPageResponse)
    fun onGetMyPageFailure(message:String)
}