package com.codepatissier.keki.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.ApplicationClass.Companion.Authorization
import com.codepatissier.keki.config.ApplicationClass.Companion.UserRole
import com.codepatissier.keki.config.ApplicationClass.Companion.userInfo
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySplashBinding
import com.codepatissier.keki.src.MainActivity
import com.codepatissier.keki.src.SellerMainActivity
import com.codepatissier.keki.src.main.auth.LoginActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    private val userRole = ApplicationClass.sSharedPreferences.getString(ApplicationClass.UserRole, "비회원")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        if (userRole == "판매자") {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, SellerMainActivity::class.java))
                finish()
            }, 1500)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 1500)
        }
    }
}