package com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail

import android.os.Bundle
import android.util.Log
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityNoticeDetailBinding
import com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail.model.ConsumerNoticeDetailResponse

class NoticeDetailActivity:BaseActivity<ActivityNoticeDetailBinding>(ActivityNoticeDetailBinding::inflate)
    , ConsumerNoticeDetailView{
    var noticeIdx: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getNoticeIdx()
        backClicked()
        showLoadingDialog(this)
        ConsumerNoticeDetailService(this).tryGetNoticeDetail(noticeIdx)
    }

    private fun getNoticeIdx(){
        noticeIdx = intent.getIntExtra("noticeIdx", 0)
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

    override fun onGetNoticeDetailSuccess(response: ConsumerNoticeDetailResponse) {
        dismissLoadingDialog()

        binding.tvNoticeDetailMain.text = response.result.noticeTitle
        binding.tvNoticeDetailText.text = response.result.noticeContent
    }

    override fun onGetNoticeDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}