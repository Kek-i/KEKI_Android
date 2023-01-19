package com.codepatissier.keki.src.main.consumer.store

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerProductFeedBinding
import com.codepatissier.keki.util.viewpager.storemain.StoreMainProductAdapter
import com.codepatissier.keki.util.viewpager.storemain.StoreMainData

class ConsumerProductFeedFragment : BaseFragment<FragmentConsumerProductFeedBinding>
    (FragmentConsumerProductFeedBinding::bind, R.layout.fragment_consumer_product_feed){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productFeedRecyclerView()
    }

    private fun productFeedRecyclerView(){
        val dataList : ArrayList<StoreMainData> = arrayListOf()
        for(i in 1 until 31) {
            dataList.apply {
                add(StoreMainData(img = R.drawable.bg_rectangle_radius_10_off_white))
            }
        }

        val storeMainProductAdapter = StoreMainProductAdapter(dataList)

        binding.recyclerProductFeed.adapter = storeMainProductAdapter
        binding.recyclerProductFeed.layoutManager= GridLayoutManager(context, 3)
    }
}