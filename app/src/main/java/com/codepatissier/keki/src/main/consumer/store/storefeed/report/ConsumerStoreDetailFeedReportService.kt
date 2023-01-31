package com.codepatissier.keki.src.main.consumer.store.storefeed.report

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerStoreDetailFeedReportService(val consumerStoreDetailFeedReportView: ConsumerStoreDetailFeedReportView) {
    fun tryPostConsumerStoreDetailFeedReportRetrofitInterface(postIdx: Int, reportName: String){
        val consumerStoreFeedDetailReportInterface =
            ApplicationClass.sRetrofit.create(ConsumerStoreDetailFeedReportRetrofitInterface::class.java)

        consumerStoreFeedDetailReportInterface.postConsumerStoreDetailFeedReport(postIdx, reportName)
            .enqueue(object: Callback<BaseResponse>{
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    consumerStoreDetailFeedReportView.onPostConsumerStoreDetailFeedReportSuccess(response.body() as BaseResponse)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    consumerStoreDetailFeedReportView.onPostConsumerStoreDetailFeedReportFailure(t.message ?: "통신 오류")
                }

            })
    }
}