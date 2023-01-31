package com.codepatissier.keki.src.main.consumer.store.storefeed.report

import com.codepatissier.keki.config.BaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ConsumerStoreDetailFeedReportRetrofitInterface {
    @POST("/posts/{postIdx}/report")
    fun postConsumerStoreDetailFeedReport(
        @Path("postIdx") postIdx: Int,
        @Body reportName: String
    ): Call<BaseResponse>
}