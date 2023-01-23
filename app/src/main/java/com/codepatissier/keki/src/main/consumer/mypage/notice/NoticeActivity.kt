package com.codepatissier.keki.src.main.consumer.mypage.notice

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityNoticeBinding
import com.codepatissier.keki.src.main.consumer.mypage.notice.model.ConsumerNoticeResponse
import com.codepatissier.keki.util.recycler.notice.NoticeAdapter
import com.codepatissier.keki.util.recycler.notice.NoticeData

class NoticeActivity :BaseActivity<ActivityNoticeBinding>(ActivityNoticeBinding::inflate), ConsumerNoticeView{
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
        val dataList : ArrayList<NoticeData> = arrayListOf()

        for(i in response.result.indices){
            dataList.apply{
                add(NoticeData(notice = response.result[i].noticeTitle))
            }
        }

        val noticeAdapter = NoticeAdapter(dataList)

        binding.rvNotice.adapter = noticeAdapter
        binding.rvNotice.layoutManager = LinearLayoutManager(this)
    }


    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

}