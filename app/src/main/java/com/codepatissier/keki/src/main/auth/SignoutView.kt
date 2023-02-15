package com.codepatissier.keki.src.main.auth

import com.codepatissier.keki.config.BaseResponse

interface SignoutView {
    fun onPatchSignoutSuccess(response: BaseResponse)
    fun onPatchSignoutFailure(message: String)
}