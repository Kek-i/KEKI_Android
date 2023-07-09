package com.codepatissier.keki.src.main.seller.mypage.orderlist

import com.codepatissier.keki.src.main.seller.mypage.orderlist.model.SellerOrderListResponse

interface SellerOrderListView {
    fun onGetSellerOrderListSuccess(response: SellerOrderListResponse)
    fun onGetSellerOrderListFailure(message: String)
}