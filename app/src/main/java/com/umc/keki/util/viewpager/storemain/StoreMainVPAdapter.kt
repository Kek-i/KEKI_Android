package com.umc.keki.util.viewpager.storemain

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.keki.src.store.ProductFeedFragment
import com.umc.keki.src.store.SellerFeedFragment


class StoreMainVPAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SellerFeedFragment()
            1 -> ProductFeedFragment()
            else -> SellerFeedFragment()
        }
    }

}