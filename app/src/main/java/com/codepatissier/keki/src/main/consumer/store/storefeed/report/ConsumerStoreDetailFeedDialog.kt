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
    var postIdx : Long? = null
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
            // ???????????? ?????? ??????
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
            // ?????? ??? ??????????????? ?????????????????????
            if(!checkIfCategoryClicked()){
                // non-check ??????
                images[num]?.setImageResource(R.drawable.ic_report_check)
                reportList[num] = true
                reportName = reportNames[num]
            }
        }else{
            // check ??????
            images[num]?.setImageResource(R.drawable.ic_none_check)
            reportList[num] = false
        }
    }

    private fun checkIfCategoryClicked(): Boolean{
        return reportList.contains(true)
    }

    override fun onPostConsumerStoreDetailFeedReportSuccess(response: BaseResponse) {
        Toast.makeText(context, "????????? ??????????????????", Toast.LENGTH_SHORT).show()
        // ???????????? ?????? ??????
        dismiss()
      }

    override fun onPostConsumerStoreDetailFeedReportFailure(message: String) {
        Toast.makeText(context, "????????? ??????????????????", Toast.LENGTH_SHORT).show()
        dismiss()
    }
}
