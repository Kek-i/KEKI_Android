package com.codepatissier.keki.src.main.auth.model

data class PostUserSignupRequest (
    val nickname : String,
    val profileImg : String? = null
    )