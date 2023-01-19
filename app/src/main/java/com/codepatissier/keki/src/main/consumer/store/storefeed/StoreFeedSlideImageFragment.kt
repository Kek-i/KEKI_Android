package com.codepatissier.keki.src.main.consumer.store.storefeed

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentStoreFeedSlideImageBinding


class StoreFeedSlideImageFragment(val image:Drawable) : BaseFragment<FragmentStoreFeedSlideImageBinding>(FragmentStoreFeedSlideImageBinding::bind, R.layout.fragment_store_feed_slide_image) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(image)
            .apply { RequestOptions().override(360, 360) }
            .into(binding.ivStoreFeedImgViewer)
    }

}