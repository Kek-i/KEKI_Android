package com.codepatissier.keki.config

import com.codepatissier.keki.config.model.PostReissueRequest
import com.codepatissier.keki.src.main.auth.model.SocialTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReissueRetrofitInterface {
    @POST("/users/reissue")
    fun reissueToken(
        @Body params: PostReissueRequest
    ): Call<SocialTokenResponse>

}