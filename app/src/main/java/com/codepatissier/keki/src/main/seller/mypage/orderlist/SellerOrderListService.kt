package com.codepatissier.keki.src.main.seller.mypage.orderlist

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.seller.mypage.orderlist.model.SellerOrderListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerOrderListService(val sellerOrderListView: SellerOrderListView) {
    fun tryGetSellerOrderList(orderStatus: String?){
        val sellerOrderListRetrofitInterface = ApplicationClass.sRetrofit.create(SellerOrderListRetrofitInterface::class.java)
        sellerOrderListRetrofitInterface.getSellerOrderList(orderStatus).enqueue(object: Callback<SellerOrderListResponse>{
            override fun onResponse(
                call: Call<SellerOrderListResponse>,
                response: Response<SellerOrderListResponse>
            ) {
                sellerOrderListView.onGetSellerOrderListSuccess(response.body() as SellerOrderListResponse)
            }

            override fun onFailure(call: Call<SellerOrderListResponse>, t: Throwable) {
                sellerOrderListView.onGetSellerOrderListFailure(t.message ?: "통신 오류")
            }

        })
    }
}