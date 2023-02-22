package com.codepatissier.keki.src.main.seller.store.storefeed.storeadd

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model.SellerFeedAddViewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerStoreFeedAddService(private val sellerStoreFeedAddView: SellerStoreFeedAddView) {

    fun tryGetStoreFeedAddView() {
        val sellerStoreFeedAddRetrofitInterface = ApplicationClass.sRetrofit.create(
            SellerStoreFeedAddRetrofitInterface::class.java)
        sellerStoreFeedAddRetrofitInterface.getStoreFeedAddView().enqueue(object:
            Callback<SellerFeedAddViewResponse> {
            override fun onResponse(call: Call<SellerFeedAddViewResponse>, response: Response<SellerFeedAddViewResponse>) {
                sellerStoreFeedAddView.onGetFeedAddViewSuccess(response.body() as SellerFeedAddViewResponse)
            }

            override fun onFailure(call: Call<SellerFeedAddViewResponse>, t: Throwable) {
                sellerStoreFeedAddView.onGetFeedAddViewFailure(t.message ?: "통신 오류")
            }
        })
    }
}