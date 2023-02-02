package com.codepatissier.keki.src.main.auth.model

data class PostSignupRequest (
    val nickname : String,
    val profileImg : String? = null
    )