package com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail

import com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail.model.ConsumerNoticeDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ConsumerNoticeDetailRetrofitInterface {
    @GET("/cs/notice/{noticeIdx}")
    fun getNoticeDetail(
        @Path("noticeIdx") noticeIdx: Int
    ): Call<ConsumerNoticeDetailResponse>
}