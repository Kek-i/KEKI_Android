package com.codepatissier.keki.src.main.seller.store.storefeed.storeedit

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.seller.store.storefeed.storeedit.model.SellerFeedEditViewResponse
import com.codepatissier.keki.src.main.seller.store.storefeed.storeedit.model.UpdateStoreFeedRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerStoreFeedEditService(private val sellerStoreFeedEditView: SellerStoreFeedEditView) {

    fun tryGetStoreFeedEditView(postIdx: Long) {
        val sellerStoreFeedEditRetrofitInterface = ApplicationClass.sRetrofit.create(
            SellerStoreFeedEditRetrofitInterface::class.java)
        sellerStoreFeedEditRetrofitInterface.getStoreFeedEditView(postIdx).enqueue(object:
            Callback<SellerFeedEditViewResponse> {
            override fun onResponse(call: Call<SellerFeedEditViewResponse>, response: Response<SellerFeedEditViewResponse>) {
                sellerStoreFeedEditView.onGetFeedEditViewSuccess(response.body() as SellerFeedEditViewResponse)
            }

            override fun onFailure(call: Call<SellerFeedEditViewResponse>, t: Throwable) {
                sellerStoreFeedEditView.onGetFeedEditViewFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryUpdateStoreFeed(postIdx: Long, updateStoreFeedRequest: UpdateStoreFeedRequest) {
        val sellerStoreFeedEditRetrofitInterface = ApplicationClass.sRetrofit.create(
            SellerStoreFeedEditRetrofitInterface::class.java)
        sellerStoreFeedEditRetrofitInterface.updateStoreFeed(postIdx, updateStoreFeedRequest).enqueue(object:
            Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.body()!!.isSuccess) {
                    sellerStoreFeedEditView.onUpdateStoreFeedSuccess(response.body() as BaseResponse)
                }
                else if (response.errorBody() != null) {
                    val errorBody = response.errorBody()!!.string()
                    sellerStoreFeedEditView.onUpdateStoreFeedFailure(errorBody)
                }
                else {
                    sellerStoreFeedEditView.onUpdateStoreFeedFailure(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                sellerStoreFeedEditView.onUpdateStoreFeedFailure(t.message ?: "통신 오류")
            }
        })
    }
}