package com.codepatissier.keki.src.main.consumer.search.searchresult

import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import com.codepatissier.keki.src.main.consumer.search.model.PatchSearchResponse
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface SearchResultRetrofitInterface {
    @GET("/posts?")
    fun getSearchResult(
        @Query("storeIdx") storeIdx: Long? = null,
        @Query("searchWord") searchWord: String? = null,
        @Query("searchTag") searchTag: String? = null,
        @Query("cursorIdx") cursorIdx: Long? = null,
        @Query("size") size: Int? = null
    ): Call<SearchResultResponse>
}   