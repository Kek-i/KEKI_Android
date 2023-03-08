package com.codepatissier.keki.src.main.seller.store.storefeed.storeadd

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendaradd.ConsumerCalendarAddRetrofitInterface
import com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model.PostStoreFeedRequest
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

    fun tryPostStoreFeed(postStoreFeedRequest: PostStoreFeedRequest) {
        val sellerStoreFeedAddRetrofitInterface = ApplicationClass.sRetrofit.create(
            SellerStoreFeedAddRetrofitInterface::class.java)
        sellerStoreFeedAddRetrofitInterface.postStoreFeed(postStoreFeedRequest).enqueue(object:
            Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.body()!!.isSuccess) {
                    sellerStoreFeedAddView.onPostStoreFeedSuccess(response.body() as BaseResponse)
                }
                else if (response.errorBody() != null) {
                    val errorBody = response.errorBody()!!.string()
                    sellerStoreFeedAddView.onPostStoreFeedFailure(errorBody)
                }
                else {
                    sellerStoreFeedAddView.onPostStoreFeedFailure(response.body()?.message.toString())
                    // 추후 수정 사항: response message 말고 에러코드로 전달해서(오버로딩) 각 코드에 맞는 경고문을 ui에 띄우기(토스트 메시지 말고)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                sellerStoreFeedAddView.onPostStoreFeedFailure(t.message ?: "통신 오류")
            }
        })
    }
}