package com.codepatissier.keki.src.main.login

import com.codepatissier.keki.src.main.login.model.SocialTokenResponse

interface LoginView {
    fun onGetUserInfoSuccess(response: SocialTokenResponse)
    fun onGetUserInfoFailure(message: String)
}