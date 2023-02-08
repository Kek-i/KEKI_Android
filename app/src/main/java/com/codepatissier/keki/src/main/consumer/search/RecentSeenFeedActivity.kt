package com.codepatissier.keki.src.main.consumer.search

import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityRecentSeenFeedBinding
import com.codepatissier.keki.src.main.consumer.search.model.RecentSeenFeedResponse
import com.codepatissier.keki.src.main.consumer.store.storefeed.DetailImageAdapter


class RecentSeenFeedActivity: BaseActivity<ActivityRecentSeenFeedBinding> (ActivityRecentSeenFeedBinding::inflate), RecentSeenFeedView{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDessertIdx()
    }

    //검색 메인 피드에서 넘어올때 postIdx 받아와서 디테일 피드 api 호출
    private fun getDessertIdx(){
        val postIdx = intent.getLongExtra("postIdx",0)
        if (postIdx != null) {
            RecentSeenFeedService(this).tryGetRecentSeenFeed(postIdx)
        }
    }



    private fun getRecentFeedView(response: RecentSeenFeedResponse){
        binding.tvStoreFeedSellerNickname.text = response.result.storeName
        binding.tvStoreFeedCakeName.text = response.result.dessertName
        binding.tvStoreFeedCakeDescription.text = response.result.description
        binding.tvStoreFeedFirstTag.text = response.result.tags[0]
        val firstTag: TextView = binding.tvStoreFeedFirstTag
        val secondTag: TextView = binding.tvStoreFeedSecondTag
        val thirdTag: TextView = binding.tvStoreFeedThirdTag
        val tagArray = arrayOf(firstTag, secondTag, thirdTag)

        for(i in response.result.tags.indices){
            tagArray[i].isVisible = true
            tagArray[i].text = "# " + response.result.tags[i]
        }

        //스토어 프로필 이미지 설정
        Glide.with(this!!)
            .load(response.result.storeProfileImg)
            .centerCrop()
            .into(binding.ivStoreFeedSeller)


        //이미지 뷰 페이저
        var img = arrayOfNulls<String>(response.result.postImgUrls.size)
        for(i in response.result.postImgUrls.indices) {
            img[i] = response.result.postImgUrls[i]
        }
        val pagerAdapter = DetailImageAdapter(this@RecentSeenFeedActivity!!, img)
        binding.vpStoreFeedImg.adapter = pagerAdapter
        binding.wormDotsIndicator.setViewPager2(binding.vpStoreFeedImg)
    }


    override fun onGetRecentSeenFeedSuccess(response: RecentSeenFeedResponse) {
        getRecentFeedView(response)
    }

    override fun onGetRecentSeenFeedFailure(message: String) {

    }

}