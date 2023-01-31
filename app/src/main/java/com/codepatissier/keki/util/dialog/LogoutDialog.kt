package com.codepatissier.keki.util.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.codepatissier.keki.config.ApplicationClass.Companion.userInfo
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.DialogLogoutBinding
import com.codepatissier.keki.src.MainActivity
import com.codepatissier.keki.src.main.auth.IntroActivity
import com.codepatissier.keki.src.main.auth.LogoutService
import com.codepatissier.keki.src.main.auth.LogoutView

class LogoutDialog(context: Context): Dialog(context), LogoutView {
    private lateinit var binding: DialogLogoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.5f)

        clickCancelBtn()
        clickLogoutBtn()
    }

    private fun clickCancelBtn() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun clickLogoutBtn() {
        binding.btnLogout.setOnClickListener {
            LogoutService(this).tryPatchUserLogout()
        }
    }

    override fun onPatchUserLogoutSuccess(response: BaseResponse) {
        this.dismiss()
        //확인 버튼 눌렀을 때 종료 flag
        userInfo.remove("Authorization")
        userInfo.commit();
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        Toast.makeText(context, "로그아웃 완료", Toast.LENGTH_SHORT).show()
    }

    override fun onPatchUserLogoutFailure(message: String) {
        Log.d("error","$message, 로그아웃 실패")
    }
}