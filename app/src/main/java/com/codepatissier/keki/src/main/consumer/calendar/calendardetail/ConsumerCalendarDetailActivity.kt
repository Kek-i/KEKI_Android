package com.codepatissier.keki.src.main.consumer.calendar.calendardetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerCalendarDetailBinding
import com.codepatissier.keki.src.main.consumer.calendar.calendardetail.model.ConsumerCalendarDetailResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendardetail.model.ResultCalendarDetail
import com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.ConsumerCalendarModifyActivity

class ConsumerCalendarDetailActivity : BaseActivity<ActivityConsumerCalendarDetailBinding>
    (ActivityConsumerCalendarDetailBinding::inflate), ConsumerCalendarDetailView {
    private var calendarIdx: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initCalendarIdx()
        setClickListenerToBackBtn()
        setClickListenerToModifyBtn()
        showLoadingDialog(this)
        ConsumerCalendarDetailService(this).tryGetCalendarDetail(calendarIdx)
    }

    override fun onRestart() {
        super.onRestart()

        showLoadingDialog(this)
        ConsumerCalendarDetailService(this).tryGetCalendarDetail(calendarIdx)
    }

    private fun initCalendarIdx() {
        calendarIdx = intent.getLongExtra("calendarIdx", 0)
    }

    private fun setLayoutFromResponse(response: ConsumerCalendarDetailResponse) {
        // 기본 정보
        binding.tvCalendarDetailTitle.text = response.result.title
        binding.tvCalendarDetailDate.text = response.result.date
        binding.tvCalendarDetailDday.text = response.result.calDate
        binding.tvCalendarDetailType.text = response.result.kindOfCalendar

        // 해시태그
        var tag: String?
        when(val i = response.result.hashTags.size) {
            0 -> binding.layoutCalendarDetailHashtag.visibility = GONE
            1 -> {
                tag = response.result.hashTags[i-1]["calendarHashTag"]
                binding.tvCalendarDetailFirstHashtag.text = "# $tag"
                binding.tvCalendarDetailFirstHashtag.visibility = VISIBLE
                binding.tvCalendarDetailSecondHashtag.visibility = GONE
                binding.tvCalendarDetailThirdHashtag.visibility = GONE
                binding.layoutCalendarDetailHashtag.visibility = VISIBLE
            }
            2 -> {
                tag = response.result.hashTags[i-2]["calendarHashTag"]
                binding.tvCalendarDetailFirstHashtag.text = "# $tag"
                tag = response.result.hashTags[i-1]["calendarHashTag"]
                binding.tvCalendarDetailSecondHashtag.text = "# $tag"
                binding.tvCalendarDetailFirstHashtag.visibility = VISIBLE
                binding.tvCalendarDetailSecondHashtag.visibility = VISIBLE
                binding.tvCalendarDetailThirdHashtag.visibility = GONE
                binding.layoutCalendarDetailHashtag.visibility = VISIBLE
            }
            3 -> {
                tag = response.result.hashTags[i-3]["calendarHashTag"]
                binding.tvCalendarDetailFirstHashtag.text = "# $tag"
                tag = response.result.hashTags[i-2]["calendarHashTag"]
                binding.tvCalendarDetailSecondHashtag.text = "# $tag"
                tag = response.result.hashTags[i-1]["calendarHashTag"]
                binding.tvCalendarDetailThirdHashtag.text = "# $tag"
                binding.tvCalendarDetailFirstHashtag.visibility = VISIBLE
                binding.tvCalendarDetailSecondHashtag.visibility = VISIBLE
                binding.tvCalendarDetailThirdHashtag.visibility = VISIBLE
                binding.layoutCalendarDetailHashtag.visibility = VISIBLE
            }
        }
    }

    override fun onGetCalendarDetailSuccess(response: ConsumerCalendarDetailResponse) {
        dismissLoadingDialog()
        setLayoutFromResponse(response)
    }

    override fun onGetCalendarDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    private fun setClickListenerToBackBtn() {
        binding.ibCalendarDetailBack.setOnClickListener {
            finish()
        }
    }

    private fun setClickListenerToModifyBtn() {
        binding.ibCalendarDetailModify.setOnClickListener {
            val intent = Intent(this, ConsumerCalendarModifyActivity::class.java)
            intent.putExtra("calendarIdx", calendarIdx)
            startActivity(intent)
        }
    }
}