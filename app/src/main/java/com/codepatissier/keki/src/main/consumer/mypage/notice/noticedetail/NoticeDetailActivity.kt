package com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail

import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityNoticeDetailBinding

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