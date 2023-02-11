package com.codepatissier.keki.src.main.consumer.search.searchresult

import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import com.codepatissier.keki.src.main.consumer.search.model.PatchSearchResponse
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchResultRetrofitInterface {
    @GET("/posts?")
    fun getSearchResult(
        @Query("storeIdx") storeIdx: Long? = null,
        @Query("searchWord") searchWord: String? = null,
        @Query("searchTag") searchTag: String? = null,
        @Query("sortType") sortType: String= "인기순",
        @Query("cursorIdx") cursorIdx: Long? = null,
        @Query("cursorPrice") cursorPrice:Int?=null,
        @Query("cursorPopularNum")cursorPopularNum:Int?=null,
        @Query("size") size: Int = 12
    ): Call<SearchResultResponse>

    @POST("/posts/{postIdx}/history")
    fun postHistory(
        @Path("postIdx") postIdx:Long
    ): Call<BaseResponse>
}   