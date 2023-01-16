package com.umc.keki.src.main.consumer.calendar

import android.os.Bundle
import com.umc.keki.config.BaseActivity
import com.umc.keki.databinding.ActivityConsumerCalendarDetailBinding

class ConsumerCalendarDetailActivity : BaseActivity<ActivityConsumerCalendarDetailBinding>(ActivityConsumerCalendarDetailBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFromIntent()
        setClickListenerToBackBtn()
    }

    private fun setFromIntent() {
        binding.tvCalendarDetailTitle.text = intent.getStringExtra("title")
        binding.tvCalendarDetailDate.text = intent.getStringExtra("date")
        binding.tvCalendarDetailDday.text = intent.getStringExtra("dday")
        binding.tvCalendarDetailType.text = intent.getStringExtra("type")
    }

    private fun setClickListenerToBackBtn() {
        binding.ibCalendarDetailBack.setOnClickListener {
            finish()
        }
    }

}