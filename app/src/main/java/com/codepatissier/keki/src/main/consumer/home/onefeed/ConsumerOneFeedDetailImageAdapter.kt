package com.codepatissier.keki.src.main.consumer.home.onefeed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ConsumerOneFeedDetailImageAdapter(fa: FragmentActivity, val images: Array<String?>): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = images.size

    override fun createFragment(position: Int): Fragment {
        return when(position){
            position -> ConsumerOneFeedSlideImageFragment(images[position]!!)
            else -> ConsumerOneFeedSlideImageFragment(images[0]!!)
        }
    }

}