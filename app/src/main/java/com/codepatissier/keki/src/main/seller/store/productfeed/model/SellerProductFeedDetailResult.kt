package com.codepatissier.keki.src.main.seller.store.productfeed.model

import com.codepatissier.keki.src.main.auth.profilesetting.model.PostNickname


data class SellerProductFeedDetailResult(
    val nickname: String,
    val dessertImg: String,
    val dessertName : String,
    val dessertPrice: Int,
    val dessertDescription: String
)