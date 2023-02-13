package com.codepatissier.keki.src.main.consumer.search.searchresult

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultService(val searchResultView: SearchResultView) {

    fun tryGetSearchResults(keyword : String, sortType : String){
        val searchResultRetrofitInterface = ApplicationClass.sRetrofit.create(SearchResultRetrofitInterface::class.java)
        searchResultRetrofitInterface.getSearchResult(searchWord = keyword, sortType = sortType).enqueue(object: Callback<SearchResultResponse>{
            override fun onResponse(call: Call<SearchResultResponse>, response: Response<SearchResultResponse>)
            {
                searchResultView.onGetSearchResultsSuccess(response.body() as SearchResultResponse)
            }
            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                searchResultView.onGetSearchResultsFailure(t.message ?: "통신 오류")
            }
        })
    }
    fun tryGetSearchResultsLatest(keyword : String, sortType : String, cursorIdx:Long, size:Int){
        val searchResultRetrofitInterface = ApplicationClass.sRetrofit.create(SearchResultRetrofitInterface::class.java)
        searchResultRetrofitInterface.getSearchResult(searchWord = keyword, sortType = sortType, cursorIdx = cursorIdx, size = size).enqueue(object: Callback<SearchResultResponse>{
            override fun onResponse(call: Call<SearchResultResponse>, response: Response<SearchResultResponse>)
            {
                searchResultView.onGetSearchResultsSuccess(response.body() as SearchResultResponse)
            }
            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                searchResultView.onGetSearchResultsFailure(t.message ?: "통신 오류")
            }
        })
    }
    fun tryGetSearchResultsPopular(keyword : String, sortType : String, cursorIdx:Long, cursorPopularNum: Int, size:Int){
        val searchResultRetrofitInterface = ApplicationClass.sRetrofit.create(SearchResultRetrofitInterface::class.java)
        searchResultRetrofitInterface.getSearchResult(searchWord = keyword, sortType = sortType, cursorIdx = cursorIdx, cursorPopularNum = cursorPopularNum,size = size).enqueue(object: Callback<SearchResultResponse>{
            override fun onResponse(call: Call<SearchResultResponse>, response: Response<SearchResultResponse>)
            {
                searchResultView.onGetSearchResultsSuccess(response.body() as SearchResultResponse)
            }
            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                searchResultView.onGetSearchResultsFailure(t.message ?: "통신 오류")
            }
        })
    }
    fun tryGetSearchResultsPrice(keyword : String, sortType : String, cursorIdx:Long, cursorPrice : Int, size:Int){
        val searchResultRetrofitInterface = ApplicationClass.sRetrofit.create(SearchResultRetrofitInterface::class.java)
        searchResultRetrofitInterface.getSearchResult(searchWord = keyword, sortType = sortType, cursorIdx = cursorIdx, cursorPrice  = cursorPrice ,size = size).enqueue(object: Callback<SearchResultResponse>{
            override fun onResponse(call: Call<SearchResultResponse>, response: Response<SearchResultResponse>)
            {
                searchResultView.onGetSearchResultsSuccess(response.body() as SearchResultResponse)
            }
            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                searchResultView.onGetSearchResultsFailure(t.message ?: "통신 오류")
            }
        })
    }


    fun tryGetTagResults(tag : String, sortType: String){
        val searchResultRetrofitInterface = ApplicationClass.sRetrofit.create(SearchResultRetrofitInterface::class.java)
        searchResultRetrofitInterface.getSearchResult(searchTag = tag, sortType = sortType).enqueue(object: Callback<SearchResultResponse>{
            override fun onResponse(call: Call<SearchResultResponse>, response: Response<SearchResultResponse>)
            {
                searchResultView.onGetSearchResultsSuccess(response.body() as SearchResultResponse)
            }
            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                searchResultView.onGetSearchResultsFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetTagResultsLatest(tag : String, sortType : String, cursorIdx:Long, size:Int){
        val searchResultRetrofitInterface = ApplicationClass.sRetrofit.create(SearchResultRetrofitInterface::class.java)
        searchResultRetrofitInterface.getSearchResult(searchTag = tag, sortType = sortType, cursorIdx = cursorIdx, size = size).enqueue(object: Callback<SearchResultResponse>{
            override fun onResponse(call: Call<SearchResultResponse>, response: Response<SearchResultResponse>)
            {
                searchResultView.onGetSearchResultsSuccess(response.body() as SearchResultResponse)
            }
            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                searchResultView.onGetSearchResultsFailure(t.message ?: "통신 오류")
            }
        })
    }
    fun tryGetTagResultsPopular(tag : String, sortType : String, cursorIdx:Long, cursorPopularNum: Int, size:Int){
        val searchResultRetrofitInterface = ApplicationClass.sRetrofit.create(SearchResultRetrofitInterface::class.java)
        searchResultRetrofitInterface.getSearchResult(searchTag = tag, sortType = sortType, cursorIdx = cursorIdx, cursorPopularNum = cursorPopularNum,size = size).enqueue(object: Callback<SearchResultResponse>{
            override fun onResponse(call: Call<SearchResultResponse>, response: Response<SearchResultResponse>)
            {
                searchResultView.onGetSearchResultsSuccess(response.body() as SearchResultResponse)
            }
            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                searchResultView.onGetSearchResultsFailure(t.message ?: "통신 오류")
            }
        })
    }
    fun tryGetTagResultsPrice(tag : String, sortType : String, cursorIdx:Long, cursorPrice : Int, size:Int){
        val searchResultRetrofitInterface = ApplicationClass.sRetrofit.create(SearchResultRetrofitInterface::class.java)
        searchResultRetrofitInterface.getSearchResult(searchTag = tag, sortType = sortType, cursorIdx = cursorIdx, cursorPrice  = cursorPrice ,size = size).enqueue(object: Callback<SearchResultResponse>{
            override fun onResponse(call: Call<SearchResultResponse>, response: Response<SearchResultResponse>)
            {
                searchResultView.onGetSearchResultsSuccess(response.body() as SearchResultResponse)
            }
            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                searchResultView.onGetSearchResultsFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostHistory(postIdx:Long){
        val searchResultRetrofitInterface = ApplicationClass.sRetrofit.create(SearchResultRetrofitInterface::class.java)
        searchResultRetrofitInterface.postHistory(postIdx = postIdx).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>)
            {
               // searchResultView.onPostHistorySuccess(response.body() as BaseResponse)
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                //searchResultView.onPostHistoryFailure(t.message ?: "통신 오류")
            }
        })
    }



}