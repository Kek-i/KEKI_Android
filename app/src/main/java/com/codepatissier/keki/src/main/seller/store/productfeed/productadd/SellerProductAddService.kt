package com.codepatissier.keki.src.main.seller.store.productfeed.productadd

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.seller.store.productfeed.productadd.model.SellerProductAddBody
import com.codepatissier.keki.src.main.seller.store.productfeed.productadd.model.SellerProductAddResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerProductAddService(val sellerProductAddView: SellerProductAddView) {
    fun tryPostProduct(body: SellerProductAddBody){
        val sellerProductAddRetrofitInterface = ApplicationClass.sRetrofit.create(
            SellerProductAddRetrofitInterface::class.java)
        sellerProductAddRetrofitInterface.postProduct(body).enqueue(object : Callback<SellerProductAddResponse>{
            override fun onResponse(
                call: Call<SellerProductAddResponse>,
                response: Response<SellerProductAddResponse>
            ) {
                sellerProductAddView.onPostProductSuccess(response.body() as SellerProductAddResponse)
            }

            override fun onFailure(call: Call<SellerProductAddResponse>, t: Throwable) {
                sellerProductAddView.onPostProductFailure(t.message ?: "통신 오류")
            }

        })
    }
}