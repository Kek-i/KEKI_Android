package com.codepatissier.keki.src.main.seller.mypage.orderInformation

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.model.SellerOrderInformationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerOrderInformationService(val sellerOrderInformationView: SellerOrderInformationView) {
    fun tryGetSellerOrderInformation(orderIdx:Int){
        val sellerOrderInformationRetrofitInterface  = ApplicationClass.sRetrofit.create(SellerOrderInformationRetrofitInterface::class.java)
        sellerOrderInformationRetrofitInterface.getSellerOrderInformation(orderIdx).enqueue(object:Callback<SellerOrderInformationResponse>{
            override fun onResponse(
                call: Call<SellerOrderInformationResponse>,
                response: Response<SellerOrderInformationResponse>
            ) {
                sellerOrderInformationView.onGetSellerOrderInformationSuccess(response.body() as SellerOrderInformationResponse)
            }

            override fun onFailure(call: Call<SellerOrderInformationResponse>, t: Throwable) {
                sellerOrderInformationView.onGetSellerOrderInformationFailure(t.message ?: "통신 오류")
            }

        })
    }
}