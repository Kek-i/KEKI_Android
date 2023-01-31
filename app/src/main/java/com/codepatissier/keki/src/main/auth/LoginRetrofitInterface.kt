package com.codepatissier.keki.src.main.auth

import com.codepatissier.keki.src.main.auth.model.PostLoginRequest
import com.codepatissier.keki.src.main.auth.model.SocialTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("/users/login")
    fun postUserInfo(
        @Body params:PostLoginRequest
    ): Call<SocialTokenResponse>

}