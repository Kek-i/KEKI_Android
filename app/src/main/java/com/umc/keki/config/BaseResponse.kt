package com.umc.keki.config

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String? = null
)