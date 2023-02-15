package com.codepatissier.keki.src.main.seller.mypage.profileEdit

import com.codepatissier.keki.src.main.seller.mypage.profileEdit.model.SellerProfileEditResponse

interface SellerProfileEditView {
    fun onPatchProfileSuccess(response: SellerProfileEditResponse)
    fun onPatchProfileFailure(message:String)
}