package com.codepatissier.keki.src.main.seller.store.productfeed.product

import com.codepatissier.keki.src.main.seller.store.productfeed.product.model.SellerProductAddResponse

interface SellerProductAddView {
    fun onPostProductSuccess(response: SellerProductAddResponse)
    fun onPostProductFailure(message:String)
}