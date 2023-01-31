package com.codepatissier.keki.util.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.DialogWithdrawalBinding
import com.codepatissier.keki.src.MainActivity
import com.codepatissier.keki.src.main.auth.SignoutService
import com.codepatissier.keki.src.main.auth.SignoutView

class WithdrawalDialog(context: Context): Dialog(context), SignoutView {
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
            SignoutService(this).tryPatchSignout()
        }
    }

    override fun onPatchSignoutSuccess(response: BaseResponse) {
        this.dismiss()
        //확인 버튼 눌렀을 때 종료 flag
        ApplicationClass.userInfo.remove("Authorization");
        ApplicationClass.userInfo.commit();
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        Toast.makeText(context, "회원탈퇴 완료", Toast.LENGTH_SHORT).show()
    }

    override fun onPatchSignoutFailure(message: String) {
    }
}