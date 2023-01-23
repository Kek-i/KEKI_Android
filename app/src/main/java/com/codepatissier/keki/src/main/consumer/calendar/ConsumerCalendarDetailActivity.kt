package com.codepatissier.keki.src.main.consumer.calendar

import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import androidx.annotation.RequiresApi
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerCalendarDetailBinding
import com.codepatissier.keki.util.recycler.calendar.CalendarAnniversaryData

class ConsumerCalendarDetailActivity : BaseActivity<ActivityConsumerCalendarDetailBinding>(ActivityConsumerCalendarDetailBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutFromIntent()
        setClickListenerToBackBtn()
    }

    private fun setLayoutFromIntent() {
//        val data =
//            if (Build.VERSION.SDK_INT >= 33)
//                intent.getParcelableExtra("detail_data", CalendarAnniversaryData::class.java)
//            else
//                @Suppress("DEPRECATION") intent.getParcelableExtra("detail_data") as? CalendarAnniversaryData
//
//        // 기본 정보
//        binding.tvCalendarDetailTitle.text = data!!.title
//        binding.tvCalendarDetailDate.text = data!!.date
//        binding.tvCalendarDetailDday.text = data!!.dday
//        binding.tvCalendarDetailType.text = data!!.type
//
//        // 해시태그
//        var tag = data.firstTag
//        if(tag != null) {
//            binding.tvCalendarDetailFirstHashtag.text = "# $tag"
//            tag = data.secondTag
//            if(tag != null) {
//                binding.tvCalendarDetailSecondHashtag.text = "# $tag"
//                tag = data.thirdTag
//                if(tag != null)
//                    binding.tvCalendarDetailThirdHashtag.text = "# $tag"
//                else binding.tvCalendarDetailThirdHashtag.visibility = GONE
//            }
//            else {
//                binding.tvCalendarDetailSecondHashtag.visibility = GONE
//                binding.tvCalendarDetailThirdHashtag.visibility = GONE
//            }
//        }
//        else binding.layoutCalendarDetailHashtag.visibility = GONE
    }

    private fun setClickListenerToBackBtn() {
        binding.ibCalendarDetailBack.setOnClickListener {
            finish()
        }
    }

}