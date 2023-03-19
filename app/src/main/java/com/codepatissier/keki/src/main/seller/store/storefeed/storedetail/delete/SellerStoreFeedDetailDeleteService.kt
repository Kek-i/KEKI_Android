package com.codepatissier.keki.src.main.seller.store.storefeed.storedetail.delete

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerStoreFeedDetailDeleteService(val sellerStoreFeedDetailDeleteView: SellerStoreFeedDetailDeleteView) {
    fun tryDeleteSellerStoreFeedDetailDeleteInterface(postIdx: Long){
        val sellerStoreFeedDetailDeleteInterface =
            ApplicationClass.sRetrofit.create(SellerStoreFeedDetailDeleteRetrofitInterface::class.java)

        sellerStoreFeedDetailDeleteInterface.deleteSellerStoreFeedDeatil(postIdx)
            .enqueue(object: Callback<BaseResponse>{
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    sellerStoreFeedDetailDeleteView.onDeleteSellerStoreFeedDetailDeleteSuccess(response.body() as BaseResponse)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    sellerStoreFeedDetailDeleteView.onDeleteSellerStoreFeedDetailDeleteFailure(t.message ?: "통신 오류")
                }

            })
    }
}