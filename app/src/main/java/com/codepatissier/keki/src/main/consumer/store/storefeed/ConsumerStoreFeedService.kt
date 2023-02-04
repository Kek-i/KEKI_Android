package com.codepatissier.keki.src.main.consumer.store.storefeed

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.search.searchresult.SearchResultRetrofitInterface
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResultResponse
import com.codepatissier.keki.src.main.consumer.store.StorefeedResultRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerStoreFeedService(val consumerStoreFeedView: ConsumerStoreFeedView) {
    fun tryGetConsumerStoreFeed(storeIdx:Long, size:Int){
        val searchResultRetrofitInterface = ApplicationClass.sRetrofit.create(StorefeedResultRetrofitInterface::class.java)
        searchResultRetrofitInterface.getStoreFeed(storeIdx = storeIdx, size=size).enqueue(object : Callback<SearchResultResponse> {
            override fun onResponse( call: Call<SearchResultResponse>, response: Response<SearchResultResponse>) {
                consumerStoreFeedView.onGetStoreFeedSuccess(response.body() as SearchResultResponse)
            }

            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                consumerStoreFeedView.onGetStoreFeedFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetConsumerStoreNextFeed(storeIdx:Long, cursorIdx:Long, size:Int){
        val searchResultRetrofitInterface = ApplicationClass.sRetrofit.create(StorefeedResultRetrofitInterface::class.java)
        searchResultRetrofitInterface.getStoreFeed(storeIdx = storeIdx, cursorIdx=cursorIdx, size=size).enqueue(object : Callback<SearchResultResponse> {
            override fun onResponse( call: Call<SearchResultResponse>, response: Response<SearchResultResponse>) {
                consumerStoreFeedView.onGetStoreNextFeedSuccess(response.body() as SearchResultResponse)
            }

            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                consumerStoreFeedView.onGetStoreNextFeedFailure(t.message ?: "통신 오류")
            }

        })
    }
}