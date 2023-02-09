package com.codepatissier.keki.src.main.seller.mypage.profileEdit

import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditBody
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditResponse
import com.codepatissier.keki.src.main.seller.mypage.profileEdit.model.SellerProfileEditBody
import com.codepatissier.keki.src.main.seller.mypage.profileEdit.model.SellerProfileEditResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface SellerProfileEditRetrofitInterface {
    @PATCH("/stores/profile")
    fun patchProfile(
        @Body parameter : SellerProfileEditBody
    ) : Call<SellerProfileEditResponse>
}