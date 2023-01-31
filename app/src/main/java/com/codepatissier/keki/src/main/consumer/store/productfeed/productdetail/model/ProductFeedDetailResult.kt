package com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail.model

import android.provider.ContactsContract.CommonDataKinds.Nickname
import com.codepatissier.keki.src.main.consumer.store.productfeed.model.Desserts

data class ProductFeedDetailResult(
    val nickname: String,
    val dessertName : String,
    val dessertPrice: Int,
    val dessertDescription: String,
    val images : List<Image>
)