package com.codepatissier.keki.src.main.consumer.mypage.notice

import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityNoticeBinding
import com.codepatissier.keki.src.main.consumer.mypage.notice.model.ConsumerNoticeResponse
import com.codepatissier.keki.util.recycler.notice.NoticeAdapter
import com.codepatissier.keki.util.recycler.notice.NoticeData

class NoticeActivity :BaseActivity<ActivityNoticeBinding>(ActivityNoticeBinding::inflate),
    ConsumerNoticeView {

    lateinit var noticeAdapter: NoticeAdapter
    val noticeDatas = mutableListOf<NoticeData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backClicked()
        showLoadingDialog(this)
        ConsumerNoticeService(this).tryGetNotice()
    }

    override fun onGetNoticeSuccess(response: ConsumerNoticeResponse) {
        dismissLoadingDialog()
        noticeRecyclerView(response)
    }

    override fun onGetNoticeFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    private fun noticeRecyclerView(response: ConsumerNoticeResponse){
        noticeAdapter = NoticeAdapter(this)
        binding.rvNotice.adapter = noticeAdapter

        for(i in response.result.indices){
            noticeDatas.apply{
                add(NoticeData(noticeTitle = response.result[i].noticeTitle,
                    noticeIdx = response.result[i].noticeIdx))
            }
        }

        noticeAdapter.noticeDatas = noticeDatas
        noticeAdapter.notifyDataSetChanged()
    }


    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

}