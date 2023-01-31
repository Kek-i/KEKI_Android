package com.codepatissier.keki.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.codepatissier.keki.config.ApplicationClass.Companion.Authorization
import com.codepatissier.keki.config.ApplicationClass.Companion.userInfo
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySplashBinding
import com.codepatissier.keki.src.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // 구매자 토큰 임의 저장
        userInfo.putString(Authorization, "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWR4IjoyLCJzdWIiOiIyIiwiZXhwIjoxNjc1NjkzODU4fQ.UHvkEhWXzpyVt7FfyLT5fYiifEm42yJmhKi1ru4zqk0")
        userInfo.commit()

     

///*
//        자동 로그인 X : 스플래시 -> 비로그인 홈화면 (고정멘트 + 랜덤 해시태그)
//        자동 로그인 O : 스플래시 -> 로그인 홈화면
//
//*/
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)
    }
}