package com.codepatissier.keki.src.main.auth

import com.codepatissier.keki.config.BaseResponse
import retrofit2.Call
import retrofit2.http.PATCH

interface SignoutRetrofitInterface {
    @PATCH("/users/signout")
    fun patchUserSignout(): Call<BaseResponse>
}