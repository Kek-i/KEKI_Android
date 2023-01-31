package com.codepatissier.keki.src.main.login.model


data class Result (
    val accessToken: String,
    val refreshToken: String,
    val role: String
    )