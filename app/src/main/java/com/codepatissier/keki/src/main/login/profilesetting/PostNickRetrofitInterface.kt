package com.codepatissier.keki.src.main.login.profilesetting

import com.codepatissier.keki.src.main.login.profilesetting.model.PostNickname
import com.codepatissier.keki.src.main.login.model.SocialLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostNickRetrofitInterface {
    @POST("/users/nickname")
    fun checkNickname(
        @Body nickname: String
    ): Call<PostNickname>
}