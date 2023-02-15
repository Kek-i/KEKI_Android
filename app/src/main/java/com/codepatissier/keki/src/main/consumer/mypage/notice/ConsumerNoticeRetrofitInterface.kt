package com.codepatissier.keki.src.main.consumer.mypage.notice

import com.codepatissier.keki.src.main.consumer.mypage.notice.model.ConsumerNoticeResponse
import retrofit2.Call
import retrofit2.http.GET

interface ConsumerNoticeRetrofitInterface {
    @GET("/cs/notice")
    fun getNotice(): Call<ConsumerNoticeResponse>

}