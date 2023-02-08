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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage


class WithdrawalDialog(context: Context): Dialog(context), SignoutView, ConsumerMyPageView {
    private lateinit var binding: DialogWithdrawalBinding
    var fbStorage : FirebaseStorage?= null
    private var fbAuth: FirebaseAuth? = null
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
        fbAuth = FirebaseAuth.getInstance();
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

        ApplicationClass.userInfo.remove("Authorization")
        ApplicationClass.userInfo.remove("UserRole")
        ApplicationClass.userInfo.remove("UserEmail")
        ApplicationClass.userInfo.commit()

        if(Profileimg != null){
            fbStorage?.reference?.child(Profileimg)?.delete()
        }

        fbAuth?.currentUser?.delete() //firebase에서도 회원 탈퇴

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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