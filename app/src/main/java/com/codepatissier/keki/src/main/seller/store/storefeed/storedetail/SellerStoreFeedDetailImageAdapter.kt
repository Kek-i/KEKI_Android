package com.codepatissier.keki.src.main.seller.store.storefeed.storedetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SellerStoreFeedDetailImageAdapter(fa: FragmentActivity, val images: Array<String?>): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = images.size

    override fun createFragment(position: Int): Fragment {
        return when(position){
            position -> SellerStoreFeedSlideImageFragment(images[position]!!)
            else -> SellerStoreFeedSlideImageFragment(images[0]!!)
        }
    }
}