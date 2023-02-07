package com.codepatissier.keki.src.main.consumer.home.onefeed

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerOneFeedDetailBinding
import com.codepatissier.keki.src.main.consumer.home.onefeed.model.ConsumerOneFeedDetailResponse

class ConsumerOneFeedDetailActivity : BaseActivity<ActivityConsumerOneFeedDetailBinding>(ActivityConsumerOneFeedDetailBinding::inflate)
    , ConsumerOneFeedDetailView{

    var postIdx: Int? = null
    private var heart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPostIdx()
        backToHome()
        showLoadingDialog(this)
        ConsumerOneFeedDetailService(this).tryGetConsumerOneFeedDetail(postIdx!!)
    }

    private fun initPostIdx(){
        postIdx = intent.getIntExtra("postIdx", -1)
    }

    @SuppressLint("SetTextI18n")
    override fun onGetConsumerOneFeedDetailSuccess(response: ConsumerOneFeedDetailResponse) {
        dismissLoadingDialog()

        Glide.with(this)
            .load(response.result.storeProfileImg)
            .centerCrop()
            .into(binding.ivStoreFeedSeller)

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
        binding.tvStoreFeedCakePrice.text = priceString.substring(0 until priceSize - 3) + "," + priceString.substring(priceSize - 3) + " 원"
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

    override fun onGetConsumerOneFeedDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    private fun backToHome(){
        binding.ivStoreFeedLeftChevron.setOnClickListener {
            finish()
        }
    }
}