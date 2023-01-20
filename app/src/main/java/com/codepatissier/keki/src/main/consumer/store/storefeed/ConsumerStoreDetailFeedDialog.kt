package com.codepatissier.keki.src.main.consumer.store.storefeed

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.DialogReportConsumerStoreDetailFeedBinding

class ConsumerStoreDetailFeedDialog(context: Context): Dialog(context){

    private lateinit var binding: DialogReportConsumerStoreDetailFeedBinding
    var reportList = Array(5){i -> false}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogReportConsumerStoreDetailFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())

        btnReport()
        checkReportList()
    }

    private fun btnReport(){
        binding.btnReport.setOnClickListener {
            // 신고하기 서버 통신

            dismiss()
        }
    }

    private fun checkReportList(){
        // first
        binding.linearDialogReportFirst.setOnClickListener {
            if(!reportList[0]){
                // non-check 상태
                binding.ivReportCheckFirst.setImageResource(R.drawable.ic_check)
                reportList[0] = true
            }else{
                // check 상태
                binding.ivReportCheckFirst.setImageResource(R.drawable.ic_none_check)
                reportList[0] = false
            }
        }

        // second
        binding.linearDialogReportSecond.setOnClickListener {
            if(!reportList[1]){
                binding.ivReportCheckSecond.setImageResource(R.drawable.ic_check)
                reportList[1] = true
            }else{
                binding.ivReportCheckSecond.setImageResource(R.drawable.ic_none_check)
                reportList[1] = false
            }
        }

        // third
        binding.linearDialogReportThird.setOnClickListener {
            if(!reportList[2]){
                binding.ivReportCheckThird.setImageResource(R.drawable.ic_check)
                reportList[2] = true
            }else{
                binding.ivReportCheckThird.setImageResource(R.drawable.ic_none_check)
                reportList[2] = false
            }
        }

        // fourth
        binding.linearDialogReportFourth.setOnClickListener {
            if(!reportList[3]){
                binding.ivReportCheckFourth.setImageResource(R.drawable.ic_check)
                reportList[3] = true
            }else{
                binding.ivReportCheckFourth.setImageResource(R.drawable.ic_none_check)
                reportList[3] = false
            }
        }

        //fifth
        binding.linearDialogReportFifth.setOnClickListener {
            if(!reportList[4]){
                binding.ivReportCheckFifth.setImageResource(R.drawable.ic_check)
                reportList[4] = true
            }else{
                binding.ivReportCheckFifth.setImageResource(R.drawable.ic_none_check)
                reportList[4] = false
            }
        }
    }
}