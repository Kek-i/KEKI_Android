package com.codepatissier.keki.src.main.consumer.mypage

import com.codepatissier.keki.src.main.consumer.mypage.model.ConsumerMyPageResponse

interface ConsumerMyPageView {
    fun onGetMyPageSuccess(response:ConsumerMyPageResponse)
    fun onGetMyPageFailure(message:String)
}