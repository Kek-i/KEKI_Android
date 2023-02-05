package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import com.codepatissier.keki.src.main.consumer.search.model.PatchSearchResponse
import com.codepatissier.keki.src.main.consumer.search.model.RecentSeenFeedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecentSeenFeedService(val recentSeenFeedView: RecentSeenFeedView) {

    fun tryGetRecentSeenFeed(postIdx : Long){
        val searchMainRetrofitInterface = ApplicationClass.sRetrofit.create(SearchMainRetrofitInterface::class.java)

        searchMainRetrofitInterface.getRecentSeenFeed(postIdx = postIdx).enqueue(object: Callback<RecentSeenFeedResponse>{
            override fun onResponse(call: Call<RecentSeenFeedResponse>,response: Response<RecentSeenFeedResponse>)
            {
                recentSeenFeedView.onGetRecentSeenFeedSuccess(response.body() as RecentSeenFeedResponse)
            }
            override fun onFailure(call: Call<RecentSeenFeedResponse>, t: Throwable) {
                recentSeenFeedView.onGetRecentSeenFeedFailure(t.message ?: "최근 본 케이크 피드 보기 오류")
            }

        })
    }


}