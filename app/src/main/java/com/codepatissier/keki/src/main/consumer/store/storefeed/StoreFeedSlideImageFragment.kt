package com.codepatissier.keki.src.main.consumer.store.storefeed

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentStoreFeedSlideImageBinding


class StoreFeedSlideImageFragment(val image:String) : BaseFragment<FragmentStoreFeedSlideImageBinding>(FragmentStoreFeedSlideImageBinding::bind, R.layout.fragment_store_feed_slide_image) {

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