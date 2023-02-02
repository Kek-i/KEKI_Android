package com.codepatissier.keki.src.main.auth.model

data class PostLoginRequest (
    val email : String,
    val provider : String
    )