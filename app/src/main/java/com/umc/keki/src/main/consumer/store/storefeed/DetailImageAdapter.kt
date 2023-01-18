package com.umc.keki.src.main.consumer.store.storefeed

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailImageAdapter(fa: FragmentActivity, val images: Array<Drawable?>): FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = images.size

    override fun createFragment(position: Int): Fragment {
        return when(position){
            position -> StoreFeedSlideImageFragment(images[position]!!)
            else -> StoreFeedSlideImageFragment(images[0]!!)
        }
    }
}