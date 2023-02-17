package com.codepatissier.keki.src.main.consumer.search

import android.os.Bundle
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.ActivityRecentSeenFeedBinding
import com.codepatissier.keki.src.main.consumer.search.model.RecentSeenFeedResponse
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreFeedDetailService
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreFeedDetailView
import com.codepatissier.keki.src.main.consumer.store.storefeed.DetailImageAdapter
import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse
import com.codepatissier.keki.src.main.consumer.store.storefeed.report.ConsumerStoreDetailFeedDialog


class RecentSeenFeedActivity: BaseActivity<ActivityRecentSeenFeedBinding> (ActivityRecentSeenFeedBinding::inflate), RecentSeenFeedView,
    ConsumerStoreFeedDetailView {

    private var heart = false
    private var postIdx:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDessertIdx()
        likeProduct()
        report()
        navigateToStoreMain()
    }

    //검색 메인 피드에서 넘어올때 postIdx 받아와서 디테일 피드 api 호출
    private fun getDessertIdx(){
        postIdx = intent.getLongExtra("postIdx",0)
        if (postIdx != null) {
            RecentSeenFeedService(this).tryGetRecentSeenFeed(postIdx)
        }
    }

    // display 별 화면에 맞는 그리드 크기 구하기
    private fun getItemWidth():Int{
        val display = this.baseContext.resources?.displayMetrics
        val displaywidth = display?.widthPixels

        return displaywidth!!
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
        val like = response.result.like
        val width = getItemWidth()

        for(i in response.result.tags.indices){
            tagArray[i].isVisible = true
            tagArray[i].text = "# " + response.result.tags[i]
        }

        //스토어 프로필 이미지 설정
        Glide.with(this!!)
            .load(response.result.storeProfileImg)
            .centerCrop()
            .override(width,width)
            .into(binding.ivStoreFeedSeller)


        //이미지 뷰 페이저
        var img = arrayOfNulls<String>(response.result.postImgUrls.size)
        for(i in response.result.postImgUrls.indices) {
            img[i] = response.result.postImgUrls[i]
        }
        val pagerAdapter = DetailImageAdapter(this@RecentSeenFeedActivity!!, img)
        binding.vpStoreFeedImg.adapter = pagerAdapter
        binding.wormDotsIndicator.setViewPager2(binding.vpStoreFeedImg)

        if(like){
            binding.ivStoreFeedHeartOff.setImageResource(R.drawable.ic_bottom_heart_on)
            heart = true
        }
    }


    override fun onGetRecentSeenFeedSuccess(response: RecentSeenFeedResponse) {
        getRecentFeedView(response)
    }

    override fun onGetRecentSeenFeedFailure(message: String) {

    }

    override fun onGetConsumerStoreFeedDetailSuccess(response: ConsumerStoreDetailFeedResponse) {}

    override fun onGetConsumerStoreFeedDetailFailure(message: String) {}

    // 피드 좋아요
    override fun onPostConsumerStoreFeedDetailLikeSuccess(response: BaseResponse) {
    }
    // 피드 좋아요 취소
    override fun onPostConsumerStoreFeedDetailLikeFailure(message: String) {
        showCustomToast("오류 : $message")
    }


    // 찜하기
    private fun likeProduct(){
        binding.ivStoreFeedHeartOff.setOnClickListener {
            if(!heart){ // 찜
                binding.ivStoreFeedHeartOff.setImageResource(R.drawable.ic_bottom_heart_on)
                heart = true
            }else{ // 해제
                binding.ivStoreFeedHeartOff.setImageResource(R.drawable.ic_bottom_heart_off)
                heart = false
            }

           postLike(postIdx!!)
        }
    }
    private fun postLike(postIdx: Long){
        ConsumerStoreFeedDetailService(this).tryPostConsumerStoreFeedDetailLike(postIdx)
    }

    // 신고하기
    private fun report(){
        binding.ivStoreFeedReport.setOnClickListener {
            var popupMenu = PopupMenu(this, it)
            popupMenu.menuInflater?.inflate(R.menu.popup_menu_report_consumer_store_detail_feed, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.popup_report -> {
                        val reportDialog = ConsumerStoreDetailFeedDialog(this!!)
                        reportDialog.postIdx = postIdx // postIdx 값 전달
                        reportDialog.show()
                        return@setOnMenuItemClickListener true
                    }else -> {
                    return@setOnMenuItemClickListener false
                }
                }
            }
        }
    }
    //뒤로가기 버튼
    private fun navigateToStoreMain(){
        binding.ivStoreFeedLeftChevron.setOnClickListener {
            finish()
        }
    }

}