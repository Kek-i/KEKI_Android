package com.umc.keki.src.main.consumer.calendar

import android.os.Bundle
import android.view.View.GONE
import com.umc.keki.config.BaseActivity
import com.umc.keki.databinding.ActivityConsumerCalendarDetailBinding

class ConsumerCalendarDetailActivity : BaseActivity<ActivityConsumerCalendarDetailBinding>(ActivityConsumerCalendarDetailBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutFromIntent()
        setClickListenerToBackBtn()
    }

    private fun setLayoutFromIntent() {
        binding.tvCalendarDetailTitle.text = intent.getStringExtra("title")
        binding.tvCalendarDetailDate.text = intent.getStringExtra("date")
        binding.tvCalendarDetailDday.text = intent.getStringExtra("dday")
        binding.tvCalendarDetailType.text = intent.getStringExtra("type")
        var tag = intent.getStringExtra("firstTag")
        if(tag != null) {
            binding.tvCalendarDetailFirstHashtag.text = "# " + intent.getStringExtra("firstTag")
            tag = intent.getStringExtra("secondTag")
            if(tag != null) {
                binding.tvCalendarDetailSecondHashtag.text = "# " + intent.getStringExtra("secondTag")
                tag = intent.getStringExtra("thirdTag")
                if(tag != null)
                    binding.tvCalendarDetailThirdHashtag.text = "# " + intent.getStringExtra("thirdTag")
                else binding.tvCalendarDetailThirdHashtag.visibility = GONE
            }
            else {
                binding.tvCalendarDetailSecondHashtag.visibility = GONE
                binding.tvCalendarDetailThirdHashtag.visibility = GONE
            }
        }
        else binding.layoutCalendarDetailHashtag.visibility = GONE
    }

    private fun setClickListenerToBackBtn() {
        binding.ibCalendarDetailBack.setOnClickListener {
            finish()
        }
    }

}