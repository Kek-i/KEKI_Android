package com.codepatissier.keki.src.main.seller.store.product

import com.codepatissier.keki.src.main.seller.store.product.model.SellerProductAddResponse

interface SellerProductAddView {
    fun onPostProductSuccess(response:SellerProductAddResponse)
    fun onPostProductFailure(message:String)
}