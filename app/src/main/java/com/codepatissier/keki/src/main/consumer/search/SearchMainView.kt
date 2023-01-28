package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import com.codepatissier.keki.src.main.consumer.search.model.PatchSearchResponse

interface SearchMainView {
    //검색 메인 페이지
    fun onGetMainSearchesSuccess(response: MainSearchesResponse)
    fun onGetMainSearchesFailure(message: String)
    //검색기록 전체 삭제
    fun onPatchSearchesSuccess(response: PatchSearchResponse)
    fun onPatchSearchesFailure(message: String)
}