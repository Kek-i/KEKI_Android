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
import com.codepatissier.keki.src.main.consumer.mypage.ConsumerMyPageService
import com.codepatissier.keki.src.main.consumer.mypage.ConsumerMyPageView
import com.codepatissier.keki.src.main.consumer.mypage.model.ConsumerMyPageResponse
import com.google.firebase.storage.FirebaseStorage

class WithdrawalDialog(context: Context): Dialog(context), SignoutView, ConsumerMyPageView {
    private lateinit var binding: DialogWithdrawalBinding
    var fbStorage : FirebaseStorage?= null
    private lateinit var Profileimg : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogWithdrawalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.5f)

        ConsumerMyPageService(this).tryGetMyPage()

        fbStorage = FirebaseStorage.getInstance()
        Profileimg = null.toString()

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
        ApplicationClass.userInfo.remove("Authorization")
        ApplicationClass.userInfo.commit()

        if(Profileimg != null){
            fbStorage?.reference?.child(Profileimg)?.delete()
        }

        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        Toast.makeText(context, "회원탈퇴 완료", Toast.LENGTH_SHORT).show()
    }

    override fun onPatchSignoutFailure(message: String) {
    }

    override fun onGetMyPageSuccess(response: ConsumerMyPageResponse) {
        if(response.result.profileImg != null){
            Profileimg = response.result.profileImg
        }

    }

    override fun onGetMyPageFailure(message: String) {
        TODO("Not yet implemented")
    }
}