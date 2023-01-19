package com.codepatissier.keki.src.main.consumer.store

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerStoreFeedBinding
import com.codepatissier.keki.util.viewpager.storemain.StoreMainData
import com.codepatissier.keki.util.viewpager.storemain.StoreMainSellerAdapter

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