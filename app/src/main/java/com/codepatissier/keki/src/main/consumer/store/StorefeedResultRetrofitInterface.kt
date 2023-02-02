package com.codepatissier.keki.src.main.consumer.store

import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StorefeedResultRetrofitInterface {
    @GET("/posts?")
    fun getStoreFeed(
        @Query("storeIdx") storeIdx: Long? = null,
        @Query("cursorIdx") cursorIdx: Long? = null,
        @Query("size") size: Int? = null
    ): Call<SearchResultResponse>
}   