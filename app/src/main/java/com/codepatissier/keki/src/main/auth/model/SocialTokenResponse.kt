package com.codepatissier.keki.src.main.auth.model


data class SocialTokenResponse (
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: Result
    )