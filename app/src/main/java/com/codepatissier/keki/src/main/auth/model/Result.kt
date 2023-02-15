package com.codepatissier.keki.src.main.auth.model


data class Result (
    val accessToken: String,
    val refreshToken: String,
    val role: String
    )