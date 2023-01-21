package com.codepatissier.keki.src.main.consumer.mypage

import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerProfileEditBinding

class ConsumerProfileEditActivity :BaseActivity<ActivityConsumerProfileEditBinding>(ActivityConsumerProfileEditBinding::inflate){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backClicked()
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }
}