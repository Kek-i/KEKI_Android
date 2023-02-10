package com.codepatissier.keki.util.viewpager.storemain.seller

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codepatissier.keki.src.main.seller.store.SellerProductFeedFragment
import com.codepatissier.keki.src.main.seller.store.SellerStoreFeedFragment


class SellerStoreMainTabAdapter(fragmentActivity: FragmentActivity, storeIdx:Long):FragmentStateAdapter(fragmentActivity) {
    val storeIdx = storeIdx

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SellerStoreFeedFragment(storeIdx)
            1 -> SellerProductFeedFragment(storeIdx)
            else -> SellerStoreFeedFragment(storeIdx)
        }
    }

}