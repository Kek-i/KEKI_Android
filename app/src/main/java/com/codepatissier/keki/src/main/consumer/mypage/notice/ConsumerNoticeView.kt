package com.codepatissier.keki.src.main.consumer.mypage.notice

import com.codepatissier.keki.src.main.consumer.mypage.notice.model.ConsumerNoticeResponse

interface ConsumerNoticeView {
    fun onGetNoticeSuccess(response: ConsumerNoticeResponse)

    fun onGetNoticeFailure(message: String)
}