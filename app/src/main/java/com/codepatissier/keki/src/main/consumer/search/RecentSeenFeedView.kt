package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.src.main.consumer.search.model.RecentSeenFeedResponse

interface RecentSeenFeedView {
    //최근 본 케이크 1개 피드
    fun onGetRecentSeenFeedSuccess(response: RecentSeenFeedResponse)
    fun onGetRecentSeenFeedFailure(message: String)

}