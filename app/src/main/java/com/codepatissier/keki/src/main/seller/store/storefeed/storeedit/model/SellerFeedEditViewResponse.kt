package com.codepatissier.keki.src.main.seller.store.storefeed.storeedit.model

import com.codepatissier.keki.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class SellerFeedEditViewResponse (
    @SerializedName("result") val result: ResultFeedEditView
) : BaseResponse()
