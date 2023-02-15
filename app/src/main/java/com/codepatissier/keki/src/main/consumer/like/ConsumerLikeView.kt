package com.codepatissier.keki.src.main.consumer.like

import com.codepatissier.keki.src.main.consumer.like.model.ConsumerLikeResponse

interface ConsumerLikeView {
    // 상품 첫 피드 가져오기
    fun onGetLikeSuccess(response: ConsumerLikeResponse)
    fun onGetLikeFailure(message:String)

    // 상품 다음 피드 가져오기
    fun onGetNextLikeSuccess(response: ConsumerLikeResponse)
    fun onGetNextLikeFailure(message:String)
}