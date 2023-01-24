package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import com.codepatissier.keki.src.main.consumer.search.model.SearchResultResponse

interface SearchResultView {
    //검색 결과 페이지
    fun onGetSearchResultsSuccess(response: SearchResultResponse)
    fun onGetSearchResultsFailure(message: String)
}