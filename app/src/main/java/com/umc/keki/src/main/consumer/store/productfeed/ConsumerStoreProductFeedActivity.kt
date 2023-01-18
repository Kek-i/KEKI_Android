package com.umc.keki.src.main.consumer.store.productfeed

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.TextView
import com.umc.keki.R
import com.umc.keki.config.BaseActivity
import com.umc.keki.databinding.ActivityStoreProductFeedBinding
import com.umc.keki.src.main.consumer.store.DetailImageAdapter

class ConsumerStoreProductFeedActivity : BaseActivity<ActivityStoreProductFeedBinding>(ActivityStoreProductFeedBinding::inflate) {

    val storeProductFeedDatas = mutableListOf<StoreProductFeedData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToStoreMain()

        storeProductFeedDatas.apply { add(StoreProductFeedData("케키 나라", "달달한 딸케", "25000원", "설명명며염ㅇ")) }
        getData()
    }

    private fun getData(){
        binding.tvProductFeedId.text = storeProductFeedDatas[0].storeName
        binding.tvProductFeedCakeName.text = storeProductFeedDatas[0].cakeName
        binding.tvProductFeedCakePrice.text = storeProductFeedDatas[0].cakePrice
        binding.tvProductFeedCakeDetail.text = storeProductFeedDatas[0].cakeDetail
       

        // 나중에 데이터 구조 보고 변경 - ConsumerStoreFeedActivity
        var img = arrayOfNulls<Drawable>(4)

        img[0] = this@ConsumerStoreProductFeedActivity?.getDrawable(R.drawable.ex_cake)
        img[1] = this@ConsumerStoreProductFeedActivity?.getDrawable(R.drawable.img_cake)
        img[2] = this@ConsumerStoreProductFeedActivity?.getDrawable(R.drawable.ex_cake)
        img[3] = this@ConsumerStoreProductFeedActivity?.getDrawable(R.drawable.img_cake)

        val pagerAdapter = DetailImageAdapter(this@ConsumerStoreProductFeedActivity!!, img)
        binding.vpProductFeedImg.adapter = pagerAdapter
        binding.wormDotsIndicator.setViewPager2(binding.vpProductFeedImg)
    }

    private fun navigateToStoreMain(){
        binding.ivProductFeedLeftChevron.setOnClickListener {
        finish()
        }
    }
}

