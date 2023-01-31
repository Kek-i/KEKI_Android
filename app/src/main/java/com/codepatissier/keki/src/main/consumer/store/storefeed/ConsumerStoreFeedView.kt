package com.codepatissier.keki.src.main.consumer.store.storefeed

import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResultResponse

interface ConsumerStoreFeedView {
    // 스토어 첫 피드 가져오기
    fun onGetStoreFeedSuccess(response:SearchResultResponse)
    fun onGetStoreFeedFailure(message:String)

    // 스토어 다음 피드 가져오기
    fun onGetStoreNextFeedSuccess(response:SearchResultResponse)
    fun onGetStoreNextFeedFailure(message:String)
}