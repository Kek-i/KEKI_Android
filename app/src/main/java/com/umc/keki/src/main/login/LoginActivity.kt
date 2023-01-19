package com.umc.keki.src.main.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.keki.config.BaseActivity
import com.umc.keki.databinding.ActivityIntroBinding
import com.umc.keki.databinding.ActivityLoginBinding
import com.umc.keki.util.viewpager.login.AccessRightDialog

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAccessRight()
    }

    private fun checkAccessRight(){
        binding.ibGoogleBtn.setOnClickListener{
            AccessRightDialog(this).show()
        }
        binding.ibNaverBtn.setOnClickListener{
            AccessRightDialog(this).show()
        }
        binding.ibKakaoBtn.setOnClickListener{
            AccessRightDialog(this).show()
        }
    }
}