package com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerStoreProductDetailFeedBinding
import com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail.model.ConsumerStoreProductFeedDetailResponse
import com.codepatissier.keki.src.main.consumer.store.storefeed.DetailImageAdapter
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat


class ConsumerStoreProductDetailFeedActivity : BaseActivity<ActivityConsumerStoreProductDetailFeedBinding>(ActivityConsumerStoreProductDetailFeedBinding::inflate), ConsumerStoreProductFeedDetailView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToStoreMain()
        getDessertIdx()
    }

    //스토어 메인 피드에서 넘어올때 dessertIdx 받아와서 디테일 피드 api 호출
    private fun getDessertIdx(){
        val clickItem = intent.getStringExtra("dessertIdx")
        if (clickItem != null) {
            ConsumerStoreProductFeedDetailService(this).tryGetProductFeedDetail(dessertIdx = clickItem.toLong())
        }
        Log.d("디저트 id체크", "디저트idx: $clickItem")
    }

    override fun onGetProductFeedSuccess(response: ConsumerStoreProductFeedDetailResponse) {
        getFeedView(response)
    }

    override fun onGetProductFeedFailure(message: String) {
        Log.d("오류", message)
    }

    private fun getFeedView(response: ConsumerStoreProductFeedDetailResponse){
        binding.tvProductFeedId.text = response.result.nickname
        binding.tvProductFeedCakeName.text = response.result.dessertName
        binding.tvProductFeedCakePrice.text = response.result.dessertPrice.toString() + "원"
        binding.tvProductFeedCakeDetail.text = response.result.dessertDescription

        Log.d("item체크", "아이템 값:${response.result}")
        //이미지개수 크기의 리스트 생성
        var img = arrayOfNulls<String>(response.result.images.size)
        for(i in response.result.images.indices) {
            img[i] = response.result.images[i].imgUrl
        }
        val pagerAdapter = DetailImageAdapter(this@ConsumerStoreProductDetailFeedActivity!!, img)
        binding.vpProductFeedImg.adapter = pagerAdapter
        binding.wormDotsIndicator.setViewPager2(binding.vpProductFeedImg)
    }


    private fun navigateToStoreMain(){
        binding.ivProductFeedLeftChevron.setOnClickListener {
        finish()
        }
    }

}


