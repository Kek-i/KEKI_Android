package com.codepatissier.keki.src.main.login

import com.codepatissier.keki.src.main.login.model.SocialLoginResponse
import com.codepatissier.keki.src.main.login.model.SocialTokenResponse

interface LoginView {
    fun onGetLoginSuccess(response: SocialLoginResponse)
    fun onGetLoginFailure(message: String)
//
//    fun onGetTokenSuccess(response: SocialTokenResponse)
//    fun onGetTokenFailure(message: String)
}