package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.search.model.PopularSearchesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val searchView: SearchView) {

    fun tryGetPopularSearches(){
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)

        searchRetrofitInterface.getPopularSearches().enqueue(object: Callback<PopularSearchesResponse>{
            override fun onResponse(
                call: Call<PopularSearchesResponse>,
                response: Response<PopularSearchesResponse>
            ) {
                searchView.onGetPopularSearchesSuccess(response.body() as PopularSearchesResponse)
            }

            override fun onFailure(call: Call<PopularSearchesResponse>, t: Throwable) {
                searchView.onGetPopularSearchesFailure(t.message ?: "통신 오류")
            }

        })
    }
}