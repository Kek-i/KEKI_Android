package com.codepatissier.keki.src.main.consumer.mypage

import android.os.Bundle
import android.webkit.WebSettings
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerConditionBinding

class ConsumerConditionActivity : BaseActivity<ActivityConsumerConditionBinding>(ActivityConsumerConditionBinding::inflate) {
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
        binding.wvComment.loadUrl("https://sites.google.com/view/keki-tos/홈")
    }
}