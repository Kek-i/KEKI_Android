package com.codepatissier.keki.src.main.consumer.store

import com.codepatissier.keki.src.main.consumer.store.model.ConsumerStoreMainResponse

interface ConsumerStoreMainView {
    fun onGetStoreMainSuccess(response:ConsumerStoreMainResponse)

    fun onGetStoreMainFailure(message:String)
}