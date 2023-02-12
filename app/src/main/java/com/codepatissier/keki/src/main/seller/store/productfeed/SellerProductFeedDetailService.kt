package com.codepatissier.keki.src.main.seller.store.productfeed

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail.model.ConsumerStoreProductFeedDetailResponse
import com.codepatissier.keki.src.main.seller.store.SellerProductFeedFragment
import com.codepatissier.keki.src.main.seller.store.productfeed.model.SellerProductFeedDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerProductFeedDetailService(val sellerProductFeedDetailView: SellerProductFeedDetailView) {
    fun tryGetProductFeedDetail(dessertIdx:Long){
        val sellerProductFeedDetailRetrofitInterface = ApplicationClass.sRetrofit.create(
            SellerProductFeedDetailRetrofitInterface::class.java)
        sellerProductFeedDetailRetrofitInterface.getProductFeedDetail(dessertIdx = dessertIdx).enqueue(object :Callback<SellerProductFeedDetailResponse>{
            override fun onResponse(call: Call<SellerProductFeedDetailResponse>, response: Response<SellerProductFeedDetailResponse>) {
                sellerProductFeedDetailView.onGetProductFeedSuccess(response.body() as SellerProductFeedDetailResponse)
            }
            override fun onFailure(call: Call<SellerProductFeedDetailResponse>, t: Throwable) {
                sellerProductFeedDetailView.onGetProductFeedFailure(t.message ?: "통신 오류")
            }

        })
    }

}