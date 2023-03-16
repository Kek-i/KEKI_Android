package com.codepatissier.keki.src.main.seller.store.productfeed.productdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentStoreFeedSlideImageBinding
import com.google.firebase.storage.FirebaseStorage


class SellerFeedSlideImageFragment(val image:String) : BaseFragment<FragmentStoreFeedSlideImageBinding>(FragmentStoreFeedSlideImageBinding::bind, R.layout.fragment_store_feed_slide_image) {
    var fbStorage : FirebaseStorage?= null
    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fbStorage = FirebaseStorage.getInstance()
        var storageRef = fbStorage?.reference?.child(image)

        storageRef?.downloadUrl?.addOnCompleteListener {
            Glide.with(this)
                .load(it.result)
                .centerCrop()
                .apply { RequestOptions().override(360, 360)}
                .into(binding.ivStoreFeedImgViewer)
        }


    }

}