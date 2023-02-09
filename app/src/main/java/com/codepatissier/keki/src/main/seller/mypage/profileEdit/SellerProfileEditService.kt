package com.codepatissier.keki.src.main.seller.mypage.profileEdit

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditBody
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditResponse
import com.codepatissier.keki.src.main.seller.mypage.profileEdit.model.SellerProfileEditBody
import com.codepatissier.keki.src.main.seller.mypage.profileEdit.model.SellerProfileEditResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerProfileEditService(val sellerProfileEditView: SellerProfileEditView) {
    fun tryPatchStoreProfile(body : SellerProfileEditBody){
        val sellerProfileEditRetofitInterface = ApplicationClass.sRetrofit.create(SellerProfileEditRetrofitInterface::class.java)
        sellerProfileEditRetofitInterface.patchProfile(body).enqueue(object : Callback<SellerProfileEditResponse>{
            override fun onResponse(
                call: Call<SellerProfileEditResponse>,
                response: Response<SellerProfileEditResponse>
            ) {
                sellerProfileEditView.onPatchProfileSuccess(response.body() as SellerProfileEditResponse)
            }

            override fun onFailure(call: Call<SellerProfileEditResponse>, t: Throwable) {
                sellerProfileEditView.onPatchProfileFailure(t.message ?: "통신 오류")
            }
        })
    }
}