package com.codepatissier.keki.util.viewpager.storemain

import java.io.Serializable

data class StoreMainProductData(
    var dessertImgUrl: String,
    var storeIdx: Long,
    var dessertIdx:Long
): Serializable
