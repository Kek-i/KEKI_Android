package com.codepatissier.keki.src.main.seller.store.productfeed.productdetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailImageAdapter(fa: FragmentActivity, val images: Array<String?>): FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = images.size

    override fun createFragment(position: Int): Fragment {

        return when(position){
            position -> SellerFeedSlideImageFragment(images[position]!!)
            else -> SellerFeedSlideImageFragment(images[0]!!)
        }
    }
}