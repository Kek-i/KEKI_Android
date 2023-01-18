package com.umc.keki.src.main.consumer.mypage

import android.os.Bundle
import com.umc.keki.config.BaseActivity
import com.umc.keki.databinding.ActivityNoticeDetailBinding

class NoticeDetailActivity:BaseActivity<ActivityNoticeDetailBinding>(ActivityNoticeDetailBinding::inflate){
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