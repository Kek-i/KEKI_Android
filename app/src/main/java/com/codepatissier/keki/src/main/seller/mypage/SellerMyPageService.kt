package com.codepatissier.keki.src.main.seller.mypage

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.mypage.model.ConsumerMyPageResponse
import com.codepatissier.keki.src.main.seller.mypage.model.SellerMyPageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerMyPageService(val sellerMyPageView:SellerMyPageView) {
    fun tryGetSellerMyPage(){
        val sellerMyPageRetrofitInterface = ApplicationClass.sRetrofit.create(SellerMyPageRetrofitInterface::class.java)
        sellerMyPageRetrofitInterface.getMyPage().enqueue(object : Callback<SellerMyPageResponse>{
            override fun onResponse(
                call: Call<SellerMyPageResponse>,
                response: Response<SellerMyPageResponse>
            ) {
                sellerMyPageView.onGetMyPageSuccess(response.body() as SellerMyPageResponse)
            }

            override fun onFailure(call: Call<SellerMyPageResponse>, t: Throwable) {
                sellerMyPageView.onGetMyPageFailure(t.message ?: "통신 오류")
            }

        })
    }
}