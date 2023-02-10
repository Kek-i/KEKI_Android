package com.codepatissier.keki.src.main.auth.model

data class PostStoreSignupRequest (
    val storeImgUrl : String? = null,
    val nickname : String,
    val address: String,
    val introduction: String,
    val orderUrl: String,
    val businessName: String,
    val brandName: String,
    val businessAddress: String,
    val businessNumber: String
    )