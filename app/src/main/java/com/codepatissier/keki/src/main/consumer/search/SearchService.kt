package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val searchView: SearchView) {

    fun tryGetMainSearches(){
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)

        searchRetrofitInterface.getSearchMain().enqueue(object: Callback<MainSearchesResponse>{
            override fun onResponse(
                call: Call<MainSearchesResponse>,
                response: Response<MainSearchesResponse>
            ) {
                searchView.onGetMainSearchesSuccess(response.body() as MainSearchesResponse)
            }

            override fun onFailure(call: Call<MainSearchesResponse>, t: Throwable) {
                searchView.onGetMainSearchesFailure(t.message ?: "통신 오류")
                println("${t.message} 삐뽀삐뽀 오류")
            }

        })
    }
}