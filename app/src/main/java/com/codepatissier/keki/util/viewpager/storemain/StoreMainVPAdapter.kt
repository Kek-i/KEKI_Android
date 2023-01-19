package com.codepatissier.keki.util.viewpager.storemain

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codepatissier.keki.src.main.consumer.store.ConsumerProductFeedFragment
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreFeedFragment


class StoreMainVPAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ConsumerStoreFeedFragment()
            1 -> ConsumerProductFeedFragment()
            else -> ConsumerStoreFeedFragment()
        }
    }

}