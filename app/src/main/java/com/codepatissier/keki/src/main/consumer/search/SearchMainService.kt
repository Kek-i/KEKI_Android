package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import com.codepatissier.keki.src.main.consumer.search.model.PatchSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMainService(val searchMainView: SearchMainView) {

    fun tryGetMainSearches(){
        val searchMainRetrofitInterface = ApplicationClass.sRetrofit.create(SearchMainRetrofitInterface::class.java)

        searchMainRetrofitInterface.getSearchMain().enqueue(object: Callback<MainSearchesResponse>{
            override fun onResponse(call: Call<MainSearchesResponse>,response: Response<MainSearchesResponse>)
            {
                searchMainView.onGetMainSearchesSuccess(response.body() as MainSearchesResponse)
            }
            override fun onFailure(call: Call<MainSearchesResponse>, t: Throwable) {
                searchMainView.onGetMainSearchesFailure(t.message ?: "메인 검색어 불러오기 통신 오류")
            }

        })
    }

    fun tryPatchSearchHistories(){
        val searchMainRetrofitInterface = ApplicationClass.sRetrofit.create(SearchMainRetrofitInterface::class.java)

        searchMainRetrofitInterface.deleteRecentSearch().enqueue(object: Callback<PatchSearchResponse>{
            override fun onResponse(call: Call<PatchSearchResponse>,response: Response<PatchSearchResponse>)
            {
                searchMainView.onPatchSearchesSuccess(response.body() as PatchSearchResponse)
            }
            override fun onFailure(call: Call<PatchSearchResponse>, t: Throwable) {
                searchMainView.onPatchSearchesFailure(t.message ?: "검색 기록 삭제 통신 오류")
            }

        })
    }

}