package com.codepatissier.keki.src.main.consumer.home

import com.codepatissier.keki.src.main.consumer.home.model.ConsumerHomeResponse
import retrofit2.Call
import retrofit2.http.GET

interface ConsumerHomeRetrofitInterface {
    @GET("/calendars/home")
    fun getConsumerHome(): Call<ConsumerHomeResponse>
}