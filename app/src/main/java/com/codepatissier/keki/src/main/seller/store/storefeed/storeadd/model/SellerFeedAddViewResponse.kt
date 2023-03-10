package com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model

import com.codepatissier.keki.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class SellerFeedAddViewResponse(
    @SerializedName("result") val result: ResultFeedAddView
) : BaseResponse()
