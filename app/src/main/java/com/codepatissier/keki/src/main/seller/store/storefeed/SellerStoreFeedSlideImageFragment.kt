package com.codepatissier.keki.src.main.seller.store.storefeed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentSellerStoreFeedSlideImageBinding

class SellerStoreFeedSlideImageFragment(val image: String) : BaseFragment<FragmentSellerStoreFeedSlideImageBinding>(FragmentSellerStoreFeedSlideImageBinding::bind, R.layout.fragment_seller_store_feed_slide_image) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val width = getItemWidth();

        Glide.with(this)
            .load(image)
            .override(width,width)
            .into(binding.ivStoreFeedImgViewer)
    }

    // display 별 화면에 맞는 그리드 크기 구하기
    private fun getItemWidth():Int{
        val display = this.context?.resources?.displayMetrics
        val displaywidth = display?.widthPixels

        return displaywidth!!
    }
}