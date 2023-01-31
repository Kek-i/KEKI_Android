package com.codepatissier.keki.src.main.consumer.store.storefeed.report

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.DialogReportConsumerStoreDetailFeedBinding

class ConsumerStoreDetailFeedDialog(context: Context): Dialog(context), ConsumerStoreDetailFeedReportView{

    private lateinit var binding: DialogReportConsumerStoreDetailFeedBinding
    var reportList = Array(5){ _ -> false}
    var postIdx : Int? = null
    var reportName: String? = null
    var linearLayouts = arrayOfNulls<LinearLayout>(5)
    var reportNames = arrayOfNulls<String>(5)
    var images = arrayOfNulls<ImageView>(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogReportConsumerStoreDetailFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())

        init()
        btnReport()
        clickLinearLayouts()
    }

    private fun init(){
        linearLayouts = arrayOf(binding.linearDialogReportFirst, binding.linearDialogReportSecond,
            binding.linearDialogReportThird, binding.linearDialogReportFourth, binding.linearDialogReportFifth)
        reportNames = arrayOf(binding.tvReportFirst.text.toString(), binding.tvReportSecond.text.toString()
            , binding.tvReportThird.text.toString(), binding.tvReportFourth.text.toString(), binding.tvReportFifth.text.toString())
        images = arrayOf(binding.ivReportCheckFirst, binding.ivReportCheckSecond, binding.ivReportCheckThird
            , binding.ivReportCheckFourth, binding.ivReportCheckFifth)
    }

    private fun btnReport(){
        binding.btnReport.setOnClickListener {
            Log.d("postIdx", postIdx.toString())
            Log.d("reportName", reportName!!)

            // 신고하기 서버 통신
            ConsumerStoreDetailFeedReportService(this).tryPostConsumerStoreDetailFeedReportRetrofitInterface(postIdx!!, reportName!!)
        }
    }

    private fun clickLinearLayouts(){
        for(i in linearLayouts.indices){
            linearLayouts[i]?.setOnClickListener {
                checkReport(i)
            }
        }
    }

    private fun checkReport(num: Int){
        if(!reportList[num]){
            // 다른 한 카테고리가 선택되어있으면
            if(!checkIfCategoryClicked()){
                // non-check 상태
                images[num]?.setImageResource(R.drawable.ic_check)
                reportList[num] = true
                reportName = reportNames[num]
            }
        }else{
            // check 상태
            images[num]?.setImageResource(R.drawable.ic_none_check)
            reportList[num] = false
        }
    }

    private fun checkIfCategoryClicked(): Boolean{
        return reportList.contains(true)
    }

    override fun onPostConsumerStoreDetailFeedReportSuccess(response: BaseResponse) {
        Toast.makeText(context, "신고를 완료했습니다", Toast.LENGTH_SHORT).show()
        Log.d("report", "success")
        // 완료되면 화면 끄기
        dismiss()
      }

    override fun onPostConsumerStoreDetailFeedReportFailure(message: String) {
        Toast.makeText(context, "신고를 실패했습니다", Toast.LENGTH_SHORT).show()
        dismiss()
    }
}