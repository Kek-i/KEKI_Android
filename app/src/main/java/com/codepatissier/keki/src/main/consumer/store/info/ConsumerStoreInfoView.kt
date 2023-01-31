package com.codepatissier.keki.src.main.consumer.store.info

import com.codepatissier.keki.src.main.consumer.store.info.model.ConsumerStoreInfoResponse

interface ConsumerStoreInfoView {
    fun onGetStoreInfoSuccess(response:ConsumerStoreInfoResponse)

    fun onGetStoreInfoFailure(message:String)
}