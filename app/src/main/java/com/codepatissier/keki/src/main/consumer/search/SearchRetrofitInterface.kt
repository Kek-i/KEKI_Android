package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import retrofit2.Call
import retrofit2.http.GET

interface SearchRetrofitInterface {
    @GET("/histories")
    fun getSearchMain(): Call<MainSearchesResponse>
}