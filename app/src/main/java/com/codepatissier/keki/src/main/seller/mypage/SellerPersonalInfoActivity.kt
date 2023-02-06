package com.codepatissier.keki.src.main.seller.mypage

import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityPersonalInfoBinding

class SellerPersonalInfoActivity : BaseActivity<ActivityPersonalInfoBinding>(ActivityPersonalInfoBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backClicked()
        webViewShow()
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

    private fun webViewShow(){
        binding.wvComment.settings.apply{
            javaScriptEnabled= true
        }
        binding.wvComment.loadUrl("https://sites.google.com/view/keki-privacy-policy/%ED%99%88")
    }
}