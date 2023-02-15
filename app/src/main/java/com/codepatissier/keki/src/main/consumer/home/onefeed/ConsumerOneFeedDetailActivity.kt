package com.codepatissier.keki.src.main.consumer.home.onefeed

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerOneFeedDetailBinding
import com.codepatissier.keki.src.main.consumer.home.onefeed.model.ConsumerOneFeedDetailResponse
import com.codepatissier.keki.src.main.consumer.search.searchresult.SearchResultService
import com.codepatissier.keki.src.main.consumer.search.searchresult.SearchResultView
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResultResponse
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainActivity
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreDetailFeedActivity
import com.codepatissier.keki.src.main.consumer.store.storefeed.report.ConsumerStoreDetailFeedDialog
import com.google.firebase.storage.FirebaseStorage

class ConsumerOneFeedDetailActivity : BaseActivity<ActivityConsumerOneFeedDetailBinding>(ActivityConsumerOneFeedDetailBinding::inflate)
    , ConsumerOneFeedDetailView, SearchResultView{

    var postIdx: Long? = null
    var storeIdx: Long? = null
    private var heart = false
    var fbStorage : FirebaseStorage?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fbStorage = FirebaseStorage.getInstance()
        initPostIdx()
        backToHome()
        showLoadingDialog(this)
        postHistory()
        ConsumerOneFeedDetailService(this).tryGetConsumerOneFeedDetail(postIdx!!)
        likeProduct()
        report()
        navigateToStoreMain()
    }

    private fun postHistory(){
        SearchResultService(this).tryPostHistory(postIdx!!)
    }

    private fun initPostIdx(){
        postIdx = intent.getLongExtra("postIdx", -1)
    }

    @SuppressLint("SetTextI18n")
    override fun onGetConsumerOneFeedDetailSuccess(response: ConsumerOneFeedDetailResponse) {
        dismissLoadingDialog()

        val defaultImg = R.drawable.ic_seller
//        if(response.result.storeProfileImg != null){
//            var storeageRef = fbStorage?.reference?.child(response.result.storeProfileImg)
//            storeageRef?.downloadUrl?.addOnCompleteListener {
//                if(it.isSuccessful){
                    Glide.with(this)
                        .load(response.result.storeProfileImg)
                        .placeholder(defaultImg)
                        .error(defaultImg)
                        .fallback(defaultImg)
                        .centerCrop()
                        .circleCrop()
                        .into(binding.ivStoreFeedSeller)
//                }
//            }
//        }

        binding.tvStoreFeedSellerNickname.text = response.result.storeName

        var img = arrayOfNulls<String>(response.result.postImgUrls.size)
        for(i in response.result.postImgUrls.indices)
            img[i] = response.result.postImgUrls[i]
        val pagerAdapter = ConsumerOneFeedDetailImageAdapter(this, img)
        binding.vpStoreFeedImg.adapter = pagerAdapter
        binding.wormDotsIndicator.setViewPager2(binding.vpStoreFeedImg)

        binding.tvStoreFeedCakeName.text = response.result.dessertName
        var priceString = response.result.dessertPrice.toString()
        var priceSize = priceString.length
        //binding.tvStoreFeedCakePrice.text = priceString.substring(0 until priceSize - 3) + "," + priceString.substring(priceSize - 3) + " 원" // 가격
        checkCakeDescription(response.result.description)
        seeMoreDescription(response.result.description)

        var tagArray = arrayOf(binding.tvStoreFeedFirstTag, binding.tvStoreFeedSecondTag, binding.tvStoreFeedThirdTag)
        for(i in response.result.tags.indices){
            tagArray[i].isVisible = true
            tagArray[i].text = "# " + response.result.tags[i]
        }

        if(response.result.like){
            binding.ivStoreFeedHeartOff.setImageResource(R.drawable.ic_bottom_heart_on)
            heart = true
        }

        storeIdx = response.result.storeIdx
    }

    private fun checkCakeDescription(description: String){
        if(description.length > 20){
            binding.tvStoreFeedCakeDescription.text = description.substring(0, 20) + " ∙∙∙ 더보기"
            seeMoreDescription(description)
        }else
            binding.tvStoreFeedCakeDescription.text = description
    }

    private fun seeMoreDescription(description: String){
        binding.tvStoreFeedCakeDescription.setOnClickListener {
            binding.tvStoreFeedCakeDescription.text = description
        }
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

            ConsumerStoreDetailFeedActivity().postLike(postIdx!!)
        }
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
                        val reportDialog = ConsumerStoreDetailFeedDialog(this)
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

    override fun onGetConsumerOneFeedDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    private fun backToHome(){
        binding.ivStoreFeedLeftChevron.setOnClickListener {
            finish()
        }
    }

    private fun navigateToStoreMain(){
        binding.tvStoreFeedSellerNickname.setOnClickListener {
            val intent = Intent(this, ConsumerStoreMainActivity::class.java)
            intent.putExtra("storeIdx", storeIdx)
            startActivity(intent)
        }
    }

    override fun onGetSearchResultsSuccess(response: SearchResultResponse) {

    }

    override fun onGetSearchResultsFailure(message: String) {

    }

    override fun onGetNextResultSuccess(response: SearchResultResponse) {

    }

    override fun onGetNextResultFailure(message: String) {

    }
}