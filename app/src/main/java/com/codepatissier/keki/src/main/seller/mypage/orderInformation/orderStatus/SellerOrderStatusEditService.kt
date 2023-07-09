package com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.model.SellerOrderStatusEditBody
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.model.SellerOrderStatusEditResponse
import com.codepatissier.keki.src.main.seller.mypage.profileEdit.model.SellerProfileEditResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerOrderStatusEditService(val sellerOrderStatusEditView: SellerOrderStatusEditView) {
    fun tryPatchOrderStatus(body : SellerOrderStatusEditBody){
        val sellerProfileEditRetofitInterface = ApplicationClass.sRetrofit.create(SellerOrderStatusRetrofitInterface::class.java)
        sellerProfileEditRetofitInterface.patchOrderStatus(body).enqueue(object : Callback<SellerOrderStatusEditResponse>{
            override fun onResponse(
                call: Call<SellerOrderStatusEditResponse>,
                response: Response<SellerOrderStatusEditResponse>
            ) {
                sellerOrderStatusEditView.onPatchOrderStatusSuccess(response.body() as SellerOrderStatusEditResponse)
            }

            override fun onFailure(call: Call<SellerOrderStatusEditResponse>, t: Throwable) {
                sellerOrderStatusEditView.onPatchOrderStatusFailure(t.message ?: "통신 오류")
            }
        })
    }
}