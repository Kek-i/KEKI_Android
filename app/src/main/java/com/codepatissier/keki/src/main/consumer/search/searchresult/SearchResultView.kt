package com.codepatissier.keki.src.main.consumer.search.searchresult

import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResultResponse

interface SearchResultView {
    //검색 결과 페이지
    fun onGetSearchResultsSuccess(response: SearchResultResponse)
    fun onGetSearchResultsFailure(message: String)

//    //조회 기록
//    fun onPostHistorySuccess(response: BaseResponse)
//    fun onPostHistoryFailure(message: String)
}