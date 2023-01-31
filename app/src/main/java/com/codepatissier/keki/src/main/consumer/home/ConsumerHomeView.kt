package com.codepatissier.keki.src.main.consumer.home

import com.codepatissier.keki.src.main.consumer.home.model.ConsumerHomeResponse

interface ConsumerHomeView {
    fun onGetConsumerHomeSuccess(response: ConsumerHomeResponse)

    fun onGetConsumerHomeFailure(message: String)
}