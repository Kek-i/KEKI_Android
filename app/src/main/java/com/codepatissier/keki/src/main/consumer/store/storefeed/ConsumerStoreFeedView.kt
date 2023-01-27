package com.codepatissier.keki.src.main.consumer.store.storefeed

import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResultResponse

interface ConsumerStoreFeedView {
    // 스토어 피드 가져오기
    fun onGetStoreFeedSuccess(response:SearchResultResponse)
    fun onGetStoreFeedFailure(message:String)
}