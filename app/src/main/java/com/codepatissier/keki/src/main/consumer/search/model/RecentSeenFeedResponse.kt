package com.codepatissier.keki.src.main.consumer.search.model

import com.codepatissier.keki.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class RecentSeenFeedResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    var result: RecentSeenFeedResult
)