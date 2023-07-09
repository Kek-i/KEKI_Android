package com.codepatissier.keki.src.main.consumer.mypage.orderInformation

import com.codepatissier.keki.src.main.consumer.mypage.orderInformation.model.ConsumerOrderInformationResponse

interface ConsumerOrderInformationView {
    fun onGetConsumerOrderInformationSuccess(response: ConsumerOrderInformationResponse)
    fun onGetConsumerOrderInformationFailure(message: String)
}