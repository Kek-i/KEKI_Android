package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import com.codepatissier.keki.src.main.consumer.search.model.PatchSearchResponse
import com.codepatissier.keki.src.main.consumer.search.model.SearchResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchRetrofitInterface {
    @GET("/histories")
    fun getSearchMain(): Call<MainSearchesResponse>

    @PATCH("/histories")
    fun deleteRecentSearch(): Call<PatchSearchResponse>

    @GET("/posts?")
    fun getSearchResult(
        @Query("storeIdx") storeIdx: Long? = null,
        @Query("searchWord") searchWord: String? = null,
        @Query("searchTag") searchTag: String? = null,
        @Query("cursorIdx") cursorIdx: Long? = null,
        @Query("size") size: Int? = null
    ): Call<SearchResultResponse>
}   