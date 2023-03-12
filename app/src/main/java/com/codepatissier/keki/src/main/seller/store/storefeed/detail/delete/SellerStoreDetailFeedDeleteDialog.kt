package com.codepatissier.keki.src.main.seller.store.storefeed.detail.delete

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.codepatissier.keki.databinding.DialogDeleteSellerStoreFeedDetailBinding

class SellerStoreDetailFeedDeleteDialog(context: Context): Dialog(context) {

    private lateinit var binding: DialogDeleteSellerStoreFeedDetailBinding
    var postIdx : Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogDeleteSellerStoreFeedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())
    }
}