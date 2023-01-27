package com.codepatissier.keki.src.main.login.profilesetting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ActivityConsumerProfileSettingBinding
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.src.MainActivity
import com.codepatissier.keki.src.main.login.IntroActivity
import com.codepatissier.keki.src.main.login.profilesetting.model.PostNickname

class EditProfileActivity : BaseActivity<ActivityConsumerProfileSettingBinding>(
    ActivityConsumerProfileSettingBinding::inflate), PostNickView {

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
            val tryNick = binding.etNickname.text.toString()
            println("$tryNick : 닉네임 들어감")
            PostNickService(this).tryPostCheckNick(tryNick)
            Toast.makeText(binding.root.context,"중복확인", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onPostNickSuccess(response: PostNickname) {
        binding.tvNamingResult.text="@string/edit_rule_pass"
    }
    @SuppressLint("SetTextI18n")
    override fun onPostNickFailure(message: String) {
        binding.tvNamingResult.text="@string/edit_rule_false"
        binding.tvNamingResult.setTextColor(resources.getColor(R.color.darkish_pink))
    }
}