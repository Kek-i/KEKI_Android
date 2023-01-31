package com.codepatissier.keki.util.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.codepatissier.keki.databinding.DialogWithdrawalBinding

class WithdrawalDialog(context: Context): Dialog(context) {
    private lateinit var binding: DialogWithdrawalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogWithdrawalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.5f)

        clickCancelBtn()
        clickWithdrawalBtn()
    }

    private fun clickCancelBtn() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun clickWithdrawalBtn() {
        binding.btnWithdrawal.setOnClickListener {

        }
    }
}