package com.codepatissier.keki.src.main.consumer.store.storefeed

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentStoreFeedSlideImageBinding
import com.google.firebase.storage.FirebaseStorage


class StoreFeedSlideImageFragment(val image:String) : BaseFragment<FragmentStoreFeedSlideImageBinding>(FragmentStoreFeedSlideImageBinding::bind, R.layout.fragment_store_feed_slide_image) {
    var fbStorage : FirebaseStorage?= null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val width = getItemWidth();

        if (image.startsWith("http")){
            Glide.with(this)
                .load(image)
                .centerCrop()
                .override(width,width)
                .into(binding.ivStoreFeedImgViewer)
        }else {
            fbStorage = FirebaseStorage.getInstance()
            var storageRef = fbStorage?.reference?.child(image)

            storageRef?.downloadUrl?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Glide.with(this)
                        .load(it.result)
                        .override(width,width)
                        .override(width, width)
                        .centerCrop()
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