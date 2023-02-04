package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import com.codepatissier.keki.src.main.consumer.search.model.PatchSearchResponse
import com.codepatissier.keki.src.main.consumer.search.model.RecentSeenFeedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SearchMainRetrofitInterface {
    @GET("/histories")
    fun getSearchMain(): Call<MainSearchesResponse>

    @PATCH("/histories")
    fun deleteRecentSearch(): Call<PatchSearchResponse>

    @GET("/post/{postIdx}")
    fun getRecentSeenFeed(
        @Path("postIdx") postIdx:Long
    ): Call<RecentSeenFeedResponse>
}   