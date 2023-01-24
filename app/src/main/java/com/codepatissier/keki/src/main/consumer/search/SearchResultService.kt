package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import com.codepatissier.keki.src.main.consumer.search.model.SearchResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultService(val searchResultView: SearchResultView) {

    fun tryGetSearchResults(keyword : String){
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getSearchResult(searchWord = "$keyword").enqueue(object: Callback<SearchResultResponse>{
            override fun onResponse(call: Call<SearchResultResponse>, response: Response<SearchResultResponse>)
            {
                searchResultView.onGetSearchResultsSuccess(response.body() as SearchResultResponse)
            }
            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                searchResultView.onGetSearchResultsFailure(t.message ?: "통신 오류")
            }
        })
    }
}