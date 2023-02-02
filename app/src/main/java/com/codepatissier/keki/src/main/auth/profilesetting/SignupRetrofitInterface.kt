package com.codepatissier.keki.src.main.auth.profilesetting

import com.codepatissier.keki.src.main.auth.model.PostSignupRequest
import com.codepatissier.keki.src.main.auth.profilesetting.model.PostNickname
import com.codepatissier.keki.src.main.auth.model.SocialTokenResponse
import com.codepatissier.keki.src.main.auth.profilesetting.model.PostNickRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupRetrofitInterface {
    @POST("/users/nickname")
    fun checkNickname(
        @Body nickname: PostNickRequest
    ): Call<PostNickname>

    @POST("/users/signup")
    fun postUserSignup(
        @Body params: PostSignupRequest
    ): Call<SocialTokenResponse>

}