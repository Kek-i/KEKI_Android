package com.umc.keki.src.main.consumer.mypage

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.keki.config.BaseActivity
import com.umc.keki.databinding.ActivityNoticeBinding
import com.umc.keki.util.recycler.notice.NoticeAdapter
import com.umc.keki.util.recycler.notice.NoticeData

class NoticeActivity :BaseActivity<ActivityNoticeBinding>(ActivityNoticeBinding::inflate){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noticeRecyclerView()
        backClicked()
    }

    private fun noticeRecyclerView(){
        val dataList : ArrayList<NoticeData> = arrayListOf()
        dataList.apply{
            add(NoticeData(notice="새로운 디저트가 등록되었어요!"))
            add(NoticeData(notice="크리스마스 기념 할인행사"))
            add(NoticeData(notice="새로운 디저트가 등록되었어요!" ))
            add(NoticeData(notice="새로운 디저트가 등록되었어요! 어서 확인하세요!" ))
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