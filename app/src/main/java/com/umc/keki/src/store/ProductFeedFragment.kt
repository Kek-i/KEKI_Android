package com.umc.keki.src.store

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.umc.keki.R
import com.umc.keki.config.BaseFragment
import com.umc.keki.databinding.FragmentProductFeedBinding
import com.umc.keki.util.viewpager.storemain.StoreMainAdapter
import com.umc.keki.util.viewpager.storemain.StoreMainData

class ProductFeedFragment : BaseFragment<FragmentProductFeedBinding>
    (FragmentProductFeedBinding::bind, R.layout.fragment_product_feed){

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

        val storeMainAdapter = StoreMainAdapter(dataList)

        binding.recyclerProductFeed.adapter = storeMainAdapter
        binding.recyclerProductFeed.layoutManager= GridLayoutManager(context, 3)
    }
}