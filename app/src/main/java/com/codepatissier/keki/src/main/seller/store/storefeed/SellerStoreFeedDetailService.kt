package com.codepatissier.keki.src.main.seller.store.storefeed

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerStoreFeedDetailService(val sellerStoreFeedDetailView: SellerStoreFeedDetailView) {
    fun tryGetSellerStoreFeedDetailInterface(storeIdx: Long, cursorIdx: Int?, size: Int?){
        val sellerStoreFeedDetailInterface = ApplicationClass.sRetrofit.create(SellerStoreFeedDetailRetrofitInterface::class.java)
        sellerStoreFeedDetailInterface.getSellerStoreFeedDetail(storeIdx, cursorIdx, size)
            .enqueue(object: Callback<ConsumerStoreDetailFeedResponse>{
                override fun onResponse(
                    call: Call<ConsumerStoreDetailFeedResponse>,
                    response: Response<ConsumerStoreDetailFeedResponse>
                ) {
                    sellerStoreFeedDetailView.onGetSellerStoreFeedDetailSuccess(response.body() as ConsumerStoreDetailFeedResponse)
                }

                override fun onFailure(call: Call<ConsumerStoreDetailFeedResponse>, t: Throwable) {
                    sellerStoreFeedDetailView.onGetSellerStoreFeedDetailFailure(t.message ?: "통신 오류")
                }

            })
    }
}