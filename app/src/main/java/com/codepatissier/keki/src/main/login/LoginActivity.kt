package com.codepatissier.keki.src.main.login

import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityLoginBinding
import com.codepatissier.keki.util.viewpager.login.AccessRightDialog

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