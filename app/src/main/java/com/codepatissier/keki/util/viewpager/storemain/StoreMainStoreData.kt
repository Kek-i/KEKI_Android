package com.codepatissier.keki.util.viewpager.storemain

import java.io.Serializable

data class StoreMainStoreData(
    var postImgUrl: String,
    var storeIdx: Long,
    var postIdx:Long
): Serializable
