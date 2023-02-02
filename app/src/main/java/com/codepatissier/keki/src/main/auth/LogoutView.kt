package com.codepatissier.keki.src.main.auth

import com.codepatissier.keki.config.BaseResponse

interface LogoutView {
    fun onPatchUserLogoutSuccess(response: BaseResponse)
    fun onPatchUserLogoutFailure(message: String)
}