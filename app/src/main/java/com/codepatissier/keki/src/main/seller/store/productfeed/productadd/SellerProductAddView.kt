package com.codepatissier.keki.src.main.seller.store.productfeed.productadd

import com.codepatissier.keki.src.main.seller.store.productfeed.productadd.model.SellerProductAddResponse

interface SellerProductAddView {
    fun onPostProductSuccess(response: SellerProductAddResponse)
    fun onPostProductFailure(message:String)
}