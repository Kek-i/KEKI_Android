package com.codepatissier.keki.src.main.auth

import com.codepatissier.keki.src.main.auth.model.SocialTokenResponse

interface LoginView {
    fun onGetUserInfoSuccess(response: SocialTokenResponse)
    fun onGetUserInfoFailure(message: String)
}