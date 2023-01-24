package com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail

import com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail.model.ConsumerNoticeDetailResponse

interface ConsumerNoticeDetailView {
    fun onGetNoticeDetailSuccess(response: ConsumerNoticeDetailResponse)

    fun onGetNoticeDetailFailure(message: String)
}