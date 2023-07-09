package com.codepatissier.keki.util.viewpager.storemain.consumer

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.codepatissier.keki.databinding.DialogOrderCancelBinding
import com.codepatissier.keki.src.main.consumer.mypage.orderInformation.cancel.ConsumerOrderCancelService
import com.codepatissier.keki.src.main.consumer.mypage.orderInformation.cancel.ConsumerOrderCancelView
import com.codepatissier.keki.src.main.consumer.mypage.orderInformation.cancel.model.ConsumerOrderCancelResponse
import com.codepatissier.keki.src.main.consumer.store.order.list.ConsumerOrderListActivity


class ConsumerOrderCancelDialog(context: Context): Dialog(context), ConsumerOrderCancelView {

    private lateinit var binding: DialogOrderCancelBinding
    var orderIdx : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogOrderCancelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())

        btnFalse()
        btnCancel()
    }

    private fun btnFalse(){
        // 취소
        binding.btnYes.setOnClickListener {
            dismiss()
        }
    }

    private fun btnCancel(){
        // 삭제하기
        binding.btnYes.setOnClickListener {
            ConsumerOrderCancelService(this).tryGetConsumerCancelInformation(orderIdx!!)
        }
    }

    override fun onGetConsumerOrderCancelSuccess(response: ConsumerOrderCancelResponse) {
        Toast.makeText(context, "주문 취소를 성공했습니다", Toast.LENGTH_SHORT).show()
        dismiss()
        val intent = Intent(context, ConsumerOrderListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        this.context.startActivity(intent)
    }

    override fun onGetConsumerOrderCancelFailure(message: String) {
        Toast.makeText(context, "주문 취소를 실패했습니다", Toast.LENGTH_SHORT).show()
        dismiss()
    }
}