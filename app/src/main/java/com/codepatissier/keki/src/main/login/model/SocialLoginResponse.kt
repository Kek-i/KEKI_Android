package com.codepatissier.keki.src.main.login.model


data class SocialLoginResponse (
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: String
    )