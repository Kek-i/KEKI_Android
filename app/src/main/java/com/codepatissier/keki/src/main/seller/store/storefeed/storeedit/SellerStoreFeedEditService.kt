package com.codepatissier.keki.src.main.seller.store.storefeed.storeedit

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.seller.store.storefeed.storeedit.model.SellerFeedEditViewResponse
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
                if (response.body()!!.isSuccess) {
                    sellerStoreFeedEditView.onGetFeedEditViewSuccess(response.body() as SellerFeedEditViewResponse)
                }
                else if (response.errorBody() != null) {
                    val errorBody = response.errorBody()!!.string()
                    sellerStoreFeedEditView.onGetFeedEditViewFailure(errorBody)
                }
                else {
                    sellerStoreFeedEditView.onGetFeedEditViewFailure(response.body()?.message.toString())
                    // 추후 수정 사항: response message 말고 에러코드로 전달해서(오버로딩) 각 코드에 맞는 경고문을 ui에 띄우기(토스트 메시지 말고)
                }
            }

            override fun onFailure(call: Call<SellerFeedEditViewResponse>, t: Throwable) {
                sellerStoreFeedEditView.onGetFeedEditViewFailure(t.message ?: "통신 오류")
            }
        })
    }
}