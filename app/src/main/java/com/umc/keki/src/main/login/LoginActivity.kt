package com.umc.keki.src.main.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.keki.databinding.ActivityLoginBinding
import com.umc.keki.util.viewpager.login.AccessRightDialog

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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