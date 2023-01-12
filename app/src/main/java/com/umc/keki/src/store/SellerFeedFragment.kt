package com.umc.keki.src.store

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.umc.keki.R
import com.umc.keki.config.BaseFragment
import com.umc.keki.databinding.FragmentSellerFeedBinding
import com.umc.keki.util.viewpager.storemain.StoreMainAdapter
import com.umc.keki.util.viewpager.storemain.StoreMainData

class SellerFeedFragment : BaseFragment<FragmentSellerFeedBinding>
    (FragmentSellerFeedBinding::bind, R.layout.fragment_seller_feed){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val dataList : ArrayList<StoreMainData> = arrayListOf()
        for(i in 1 until 31) {
            dataList.apply {
                add(StoreMainData(img = R.drawable.bg_rectangle_radius_10_off_white))
            }
        }

        val storeMainAdapter = StoreMainAdapter(dataList)

        binding.recyclerSellerFeed.adapter = storeMainAdapter
        binding.recyclerSellerFeed.layoutManager=GridLayoutManager(context, 3)
    }
}