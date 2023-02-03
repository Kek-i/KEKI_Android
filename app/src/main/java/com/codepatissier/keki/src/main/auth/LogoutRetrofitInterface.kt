package com.codepatissier.keki.src.main.auth

import com.codepatissier.keki.config.BaseResponse
import retrofit2.Call
import retrofit2.http.PATCH

interface LogoutRetrofitInterface {
    @PATCH("/users/logout")
    fun patchUserLogout(): Call<BaseResponse>
}