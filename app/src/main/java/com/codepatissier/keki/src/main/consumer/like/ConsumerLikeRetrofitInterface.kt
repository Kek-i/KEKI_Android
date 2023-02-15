package com.codepatissier.keki.src.main.consumer.like

import com.codepatissier.keki.src.main.consumer.like.model.ConsumerLikeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ConsumerLikeRetrofitInterface {
    @GET("/posts/likes")
    fun getLike(
        @Query("cursorDate") cursorDate:String? = null,
        @Query("size") size:Int? = null
    ): Call<ConsumerLikeResponse>
}