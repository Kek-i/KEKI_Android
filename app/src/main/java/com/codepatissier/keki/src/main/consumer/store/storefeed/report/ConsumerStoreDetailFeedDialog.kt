package com.codepatissier.keki.src.main.consumer.store.storefeed.report

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.DialogReportConsumerStoreDetailFeedBinding

class ConsumerStoreDetailFeedDialog(context: Context): Dialog(context), ConsumerStoreDetailFeedReportView{

    private lateinit var binding: DialogReportConsumerStoreDetailFeedBinding
    var reportList = Array(5){ _ -> false}
    var postIdx : Int? = null
    var reportName: String? = null
    var reportNames = arrayOfNulls<String>(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogReportConsumerStoreDetailFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())

        init()
        btnReport()
        checkReportList()
    }

    private fun init(){
        //reportNames = (binding.tvReportFirst.text.toString(), binding.tv)
    }

    private fun btnReport(){
        binding.btnReport.setOnClickListener {
            Log.d("postIdx", postIdx.toString())
            Log.d("reportName", reportName!!)
            // 신고하기 서버 통신
            //ConsumerStoreDetailFeedReportService(this).tryPostConsumerStoreDetailFeedReportRetrofitInterface(postIdx!!, reportName!!)
        }
    }

    private fun checkReportList(){
        // first
        binding.linearDialogReportFirst.setOnClickListener {
            if(!reportList[0]){
                // 다른 한 카테고리가 선택되어있으면
                if(!checkIfCategoryClicked()){
                    // non-check 상태
                    binding.ivReportCheckFirst.setImageResource(R.drawable.ic_check)
                    reportList[0] = true
                    reportName = binding.tvReportFirst.text.toString()
                }
            }else{
                // check 상태
                binding.ivReportCheckFirst.setImageResource(R.drawable.ic_none_check)
                reportList[0] = false
            }
        }

        // second
        binding.linearDialogReportSecond.setOnClickListener {
            if(!reportList[1]){
                if(!checkIfCategoryClicked()){
                    binding.ivReportCheckSecond.setImageResource(R.drawable.ic_check)
                    reportList[1] = true
                    reportName = binding.tvReportSecond.text.toString()
                }
            }else{
                binding.ivReportCheckSecond.setImageResource(R.drawable.ic_none_check)
                reportList[1] = false
            }
        }

        // third
        binding.linearDialogReportThird.setOnClickListener {
            if(!reportList[2]){
                if(!checkIfCategoryClicked()){
                    binding.ivReportCheckThird.setImageResource(R.drawable.ic_check)
                    reportList[2] = true
                    reportName = binding.tvReportThird.text.toString()
                }
            }else{
                binding.ivReportCheckThird.setImageResource(R.drawable.ic_none_check)
                reportList[2] = false
            }
        }

        // fourth
        binding.linearDialogReportFourth.setOnClickListener {
            if(!reportList[3]){
                if(!checkIfCategoryClicked()){
                    binding.ivReportCheckFourth.setImageResource(R.drawable.ic_check)
                    reportList[3] = true
                    reportName = binding.tvReportFourth.text.toString()
                }
            }else{
                binding.ivReportCheckFourth.setImageResource(R.drawable.ic_none_check)
                reportList[3] = false
            }
        }

        //fifth
        binding.linearDialogReportFifth.setOnClickListener {
            if(!reportList[4]){
                if(!checkIfCategoryClicked()){
                    binding.ivReportCheckFifth.setImageResource(R.drawable.ic_check)
                    reportList[4] = true
                    reportName = binding.tvReportFifth.text.toString()
                }
            }else{
                binding.ivReportCheckFifth.setImageResource(R.drawable.ic_none_check)
                reportList[4] = false
            }
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