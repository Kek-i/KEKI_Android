package com.codepatissier.keki.src.main.login

import com.codepatissier.keki.src.main.login.model.PostLoginRequest
import com.codepatissier.keki.src.main.login.model.SocialTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("/users/login")
    fun postUserInfo(
        @Body params:PostLoginRequest
    ): Call<SocialTokenResponse>

}