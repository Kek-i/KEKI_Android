package com.codepatissier.keki.src.main.seller.store.productfeed

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.seller.store.productfeed.product.model.SellerProductAddBody
import com.codepatissier.keki.src.main.seller.store.productfeed.model.SellerProductFeedDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

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

    fun tryDelProductFeedDetail(dessertIdx:Long){
        val sellerProductFeedDetailRetrofitInterface = ApplicationClass.sRetrofit.create(
            SellerProductFeedDetailRetrofitInterface::class.java)
        sellerProductFeedDetailRetrofitInterface.delProductFeedDetail(dessertIdx = dessertIdx).enqueue(object :Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                sellerProductFeedDetailView.onDelProductFeedSuccess(response.body() as BaseResponse)
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                sellerProductFeedDetailView.onDelProductFeedFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPatchProductFeedDetail(dessertIdx: Long, body: SellerProductAddBody){
        val sellerProductFeedDetailRetrofitInterface = ApplicationClass.sRetrofit.create(
            SellerProductFeedDetailRetrofitInterface::class.java)
        sellerProductFeedDetailRetrofitInterface.patchProductFeedDetail(dessertIdx, body).enqueue(object :Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                sellerProductFeedDetailView.onPatchProductFeedSuccess(response.body() as BaseResponse)
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                sellerProductFeedDetailView.onPatchProductFeedFailure(t.message ?: "통신 오류")
            }

        })
    }

}