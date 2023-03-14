package com.codepatissier.keki.config.model

data class PostReissueRequest (
    val email : String,
    val provider : String,
    val refreshToken : String
    )