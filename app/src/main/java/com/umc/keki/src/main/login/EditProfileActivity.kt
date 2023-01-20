package com.umc.keki.src.main.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.codepatissier.keki.databinding.ActivityConsumerProfileSettingBinding
import com.umc.keki.config.BaseActivity
import com.umc.keki.src.MainActivity

class EditProfileActivity : BaseActivity<ActivityConsumerProfileSettingBinding>(
    ActivityConsumerProfileSettingBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clickConfirm()
        clickBack()
        doubleCheck()
    }

    //완료 버튼 클릭
    private fun clickConfirm(){
        binding.tvCheck.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    //돌아가기 버튼 클릭
    private fun clickBack(){
        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }
    }

    //중복확인 버튼 클릭
    private fun doubleCheck() {
        binding.btnDoubleCheck.setOnClickListener {
            Toast.makeText(binding.root.context,"중복확인", Toast.LENGTH_SHORT).show()
        }
    }
}