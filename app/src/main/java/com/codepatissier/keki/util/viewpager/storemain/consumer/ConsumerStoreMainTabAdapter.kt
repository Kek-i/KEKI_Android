package com.codepatissier.keki.util.viewpager.storemain.consumer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codepatissier.keki.src.main.consumer.store.ConsumerProductFeedFragment
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreFeedFragment


class ConsumerStoreMainTabAdapter(fragmentActivity: FragmentActivity, storeIdx:Long):FragmentStateAdapter(fragmentActivity) {
    val storeIdx = storeIdx

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ConsumerStoreFeedFragment(storeIdx)
            1 -> ConsumerProductFeedFragment(storeIdx)
            else -> ConsumerStoreFeedFragment(storeIdx)
        }
    }

}