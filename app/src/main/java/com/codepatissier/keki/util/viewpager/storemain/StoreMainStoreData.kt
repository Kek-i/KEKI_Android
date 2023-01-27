package com.codepatissier.keki.util.viewpager.storemain

import java.io.Serializable

data class StoreMainStoreData(
    var img: String,
    var storeIdx: Long,
    var postIdx:Long
): Serializable
