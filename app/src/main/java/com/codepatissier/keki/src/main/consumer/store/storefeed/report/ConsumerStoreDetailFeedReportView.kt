package com.codepatissier.keki.src.main.consumer.store.storefeed.report

import com.codepatissier.keki.config.BaseResponse

interface ConsumerStoreDetailFeedReportView {
    fun onPostConsumerStoreDetailFeedReportSuccess(response: BaseResponse)

    fun onPostConsumerStoreDetailFeedReportFailure(message: String)
}