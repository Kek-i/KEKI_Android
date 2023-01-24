package com.codepatissier.keki.src.main.consumer.store.productfeed

import android.graphics.drawable.Drawable
import android.os.Bundle
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.src.main.consumer.store.storefeed.DetailImageAdapter
import com.codepatissier.keki.databinding.ActivityConsumerStoreProductDetailFeedBinding

class ConsumerStoreProductDetailFeedActivity : BaseActivity<ActivityConsumerStoreProductDetailFeedBinding>(ActivityConsumerStoreProductDetailFeedBinding::inflate) {

    val storeProductFeedData = mutableListOf<StoreProductFeedData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToStoreMain()

        storeProductFeedData.apply { add(StoreProductFeedData("케키 나라", "딸기 티라미수", "25000원", "설명설명\n레터링 추가 +2000\n디자인 추가 +5000")) }
        getData()
    }

    private fun getData(){
        binding.tvProductFeedId.text = storeProductFeedData[0].storeName
        binding.tvProductFeedCakeName.text = storeProductFeedData[0].cakeName
        binding.tvProductFeedCakePrice.text = storeProductFeedData[0].cakePrice
        binding.tvProductFeedCakeDetail.text = storeProductFeedData[0].cakeDetail
       
        /*
        이 부분 원래 String 값이어야 하는데 이미지를 임시로 넣어야해서 Drawable로 해놨었어요!
        나중에 이 부분 수정해주세요!!

        var img = arrayOfNulls<Drawable>(4)

        img[0] = this@ConsumerStoreProductDetailFeedActivity?.getDrawable(R.drawable.ex_cake)
        img[1] = this@ConsumerStoreProductDetailFeedActivity?.getDrawable(R.drawable.img_cake)
        img[2] = this@ConsumerStoreProductDetailFeedActivity?.getDrawable(R.drawable.ex_cake)
        img[3] = this@ConsumerStoreProductDetailFeedActivity?.getDrawable(R.drawable.img_cake)


        val pagerAdapter = DetailImageAdapter(this@ConsumerStoreProductDetailFeedActivity!!, img)
        binding.vpProductFeedImg.adapter = pagerAdapter
        binding.wormDotsIndicator.setViewPager2(binding.vpProductFeedImg)
        */

    }

    private fun navigateToStoreMain(){
        binding.ivProductFeedLeftChevron.setOnClickListener {
        finish()
        }
    }
}

