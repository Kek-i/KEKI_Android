package com.umc.keki.src.storeMain

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.umc.keki.R
import com.umc.keki.config.BaseFragment
import com.umc.keki.databinding.FragmentConsumerStoreFeedBinding
import com.umc.keki.util.viewpager.storemain.StoreMainData
import com.umc.keki.util.viewpager.storemain.StoreMainSellerAdapter

class ConsumerStoreFeedFragment : BaseFragment<FragmentConsumerStoreFeedBinding>
    (FragmentConsumerStoreFeedBinding::bind, R.layout.fragment_consumer_store_feed){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sellerFeedRecyclerView()
    }

    private fun sellerFeedRecyclerView(){
        val dataList : ArrayList<StoreMainData> = arrayListOf()
        for(i in 1 until 31) {
            dataList.apply {
                add(StoreMainData(img = R.drawable.bg_rectangle_radius_10_off_white))
            }
        }

        val storeMainSellerAdapter = StoreMainSellerAdapter(dataList)

        binding.recyclerSellerFeed.adapter = storeMainSellerAdapter
        binding.recyclerSellerFeed.layoutManager=GridLayoutManager(context, 3)

    }
}