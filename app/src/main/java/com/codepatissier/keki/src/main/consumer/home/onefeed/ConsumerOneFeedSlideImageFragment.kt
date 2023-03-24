package com.codepatissier.keki.src.main.consumer.home.onefeed

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.request.RequestOptions
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerOneFeedSlideImageBinding
import com.google.firebase.storage.FirebaseStorage


class ConsumerOneFeedSlideImageFragment(val image: String) : BaseFragment<FragmentConsumerOneFeedSlideImageBinding>(FragmentConsumerOneFeedSlideImageBinding::bind, R.layout.fragment_consumer_one_feed_slide_image) {
    var fbStorage : FirebaseStorage?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fbStorage = FirebaseStorage.getInstance()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val width = getItemWidth()

        if (image.startsWith("http")){
            Glide.with(this)
                .load(image)
                .override(width, width)
                .centerCrop()
                .into(binding.ivStoreFeedImgViewer)
        }else {
            var storageRef = fbStorage?.reference?.child(image)
            storageRef?.downloadUrl?.addOnCompleteListener {
                if(it.isSuccessful){
                Glide.with(this)
                    .load(it.result)
                    .centerCrop()
                    .override(width, width)
                    .into(binding.ivStoreFeedImgViewer)
                }
            }
        }
    }

    // display 별 화면에 맞는 그리드 크기 구하기
    private fun getItemWidth():Int{
        val display = this.context?.resources?.displayMetrics
        val displaywidth = display?.widthPixels

        return displaywidth!!
    }

}